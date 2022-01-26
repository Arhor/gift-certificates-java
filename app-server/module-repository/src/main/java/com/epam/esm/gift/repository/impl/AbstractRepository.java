package com.epam.esm.gift.repository.impl;

import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.epam.esm.gift.model.Auditable;
import com.epam.esm.gift.model.Entity;
import com.epam.esm.gift.repository.BaseRepository;
import com.epam.esm.gift.repository.bootstrap.EntityModel;
import com.epam.esm.gift.repository.bootstrap.Queries;
import com.epam.esm.gift.repository.bootstrap.QueryProvider;
import com.epam.esm.gift.repository.bootstrap.RepositoryIntrospector;

public abstract class AbstractRepository<T extends Entity<K>, K extends Serializable>
    implements BaseRepository<T, K>, InitializingBean {

    protected RowMapper<T> rowMapper;

    @Autowired
    protected NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private RepositoryIntrospector introspector;

    @Autowired
    private QueryProvider queryProvider;

    protected EntityModel entityModel;
    protected Queries queries;

    protected AbstractRepository(final RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    @Override
    public void afterPropertiesSet() {
        entityModel = introspector.introspect(this.getClass());
        queries = queryProvider.buildQueries(entityModel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T create(final T entity) {
        if (entity instanceof Auditable) {
            ((Auditable<LocalDateTime>) entity).setDateTimeCreated(currentTime());
        }
        var insertOneQuery = queries.insertOne();
        var parameters = new BeanPropertySqlParameterSource(entity);

        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(insertOneQuery, parameters, keyHolder);

        var returnedKeys = keyHolder.getKeys();

        if (returnedKeys != null) {
            entity.setId((K) returnedKeys.get("id"));
        }
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T update(final T entity) {
        if (entity instanceof Auditable) {
            ((Auditable<LocalDateTime>) entity).setDateTimeUpdated(currentTime());
        }
        var updateOneQuery = queries.updateOne();
        var parameters = new BeanPropertySqlParameterSource(entity);

        int rowsUpdated = jdbcTemplate.update(updateOneQuery, parameters);

        if (rowsUpdated == 1) {
            return entity;
        }
        throw new IllegalStateException("Number of updated rows is not equal to 1");
    }

    @Override
    public List<T> findAll() {
        var selectAllQuery = queries.selectAll();
        return jdbcTemplate.query(selectAllQuery, rowMapper);
    }

    @Override
    public Optional<T> findById(final K id) {
        var params = Map.of("id", id);
        var selectOneQuery = queries.selectOne();
        return jdbcTemplate.query(selectOneQuery, params, rowMapper).stream().findFirst();
    }

    @Override
    public void delete(final T entity) {
        var id = entity.getId();
        if (id != null) {
            deleteById(id);
        }
    }

    @Override
    public void deleteById(final K id) {
        var params = Map.of("id", id);
        var deleteOneQuery = queries.deleteOne();
        jdbcTemplate.update(deleteOneQuery, params);
    }

    private LocalDateTime currentTime() {
        return LocalDateTime.now(Clock.systemUTC()).truncatedTo(ChronoUnit.MILLIS);
    }
}