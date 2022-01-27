package com.epam.esm.gift.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.epam.esm.gift.repository.TagRepository;
import com.epam.esm.gift.repository.model.Tag;

@Repository
public class TagRepositoryImpl extends AbstractRepository<Tag, Long> implements TagRepository {

    @Autowired
    public TagRepositoryImpl(final RowMapper<Tag> rowMapper) {
        super(rowMapper);
    }

    @Override
    public Optional<Tag> findTagByName(final String name) {
        var params = Map.of("name", name);
        var selectTagByNameQuery = queries.selectAll() + " WHERE name = :name";
        return jdbcTemplate.query(selectTagByNameQuery, params, rowMapper).stream().findFirst();
    }

    @Override
    public List<Tag>  findTagByNames(final List<String> names) {
        var params = Map.of("names", names);
        var selectTagsByNameInQuery = queries.selectAll() + " WHERE name IN (:names)";
        return jdbcTemplate.query(selectTagsByNameInQuery, params, rowMapper);
    }

    @Override
    public List<Tag> findTagsByCertificateId(final Long certificateId) {
        return jdbcTemplate.query(
            "SELECT t.id, t.name FROM tags t JOIN certificates_has_tags cht on t.id = cht.tags_id WHERE cht.certificates_id = :certificateId",
            Map.of("certificateId", certificateId),
            rowMapper
        );
    }

    @Override
    public void addTagsToCertificate(final Long certificateId, final List<Tag> tags) {
        if (tags.isEmpty()) {
            return;
        }

        var params = new HashMap<String, Object>();
        params.put("certificateId", certificateId);

        var query = new StringJoiner(
            ", ",
            "INSERT INTO certificates_has_tags (certificates_id, tags_id) VALUES ",
            ""
        );

        for (int i = 0; i < tags.size(); i++) {
            var tag = tags.get(i);
            var tagIdPlaceholder = "tag" + i;
            query.add("(:certificateId, :" + tagIdPlaceholder + ")");
            params.put(tagIdPlaceholder, tag.getId());
        }

        jdbcTemplate.update(query.toString(), params);
    }

    @Override
    public void removeAllTagsFromCertificate(final Long certificateId) {
        jdbcTemplate.update(
            "DELETE FROM certificates_has_tags cht WHERE cht.certificates_id = :certificateId",
            Map.of("certificateId", certificateId)
        );
    }
}
