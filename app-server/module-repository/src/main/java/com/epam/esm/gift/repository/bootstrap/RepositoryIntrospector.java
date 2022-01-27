package com.epam.esm.gift.repository.bootstrap;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.epam.esm.gift.repository.annotation.Column;
import com.epam.esm.gift.repository.annotation.Id;
import com.epam.esm.gift.repository.annotation.Table;
import com.epam.esm.gift.repository.impl.AbstractRepository;

@Component
public class RepositoryIntrospector {

    private static final int ENTITY_TYPE_ARG_IDX = 0;

    private final Map<Class<?>, EntityModel> modelByType = new ConcurrentHashMap<>();

    public <T extends AbstractRepository<?, ?>> EntityModel introspect(final Class<T> repositoryClass) {

        var entityClass = determineEntityClass(repositoryClass);

        return modelByType.computeIfAbsent(entityClass, this::computeEntityModel);
    }

    private EntityModel computeEntityModel(final Class<?> entityClass) {
        var tableName = extractTableName(entityClass);

        var columnProperties = Arrays.stream(entityClass.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(Column.class))
            .collect(Collectors.toList());

        var idColumn = columnProperties.stream()
            .filter(field -> field.isAnnotationPresent(Id.class))
            .map(this::introspectColumnProperty)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Cannot find property annotated with Id"));

        var restColumns = columnProperties.stream()
            .filter(field -> !field.isAnnotationPresent(Id.class))
            .map(this::introspectColumnProperty)
            .collect(Collectors.toList());

        return new EntityModel(tableName, idColumn, restColumns);
    }

    private String extractTableName(final Class<?> entityClass) {
        return Optional.ofNullable(entityClass.getAnnotation(Table.class))
            .map(Table::name)
            .or(() -> Optional.of(entityClass.getSimpleName()))
            .orElseThrow(() -> new IllegalStateException("Cannot determine table name"));
    }

    private ColumnProperty introspectColumnProperty(final Field property) {
        var propName = property.getName();
        var realName = Optional.ofNullable(property.getAnnotation(Column.class))
            .map(Column::name)
            .filter(it -> !it.isBlank())
            .orElse(propName);

        return new ColumnProperty(propName, realName);
    }

    private <T extends AbstractRepository<?, ?>> Class<?> determineEntityClass(final Class<T> repositoryClass) {

        if (AbstractRepository.class.equals(repositoryClass.getSuperclass())
            && repositoryClass.getGenericSuperclass() instanceof ParameterizedType) {

            var parameterizedType = (ParameterizedType) repositoryClass.getGenericSuperclass();
            var typeArguments = parameterizedType.getActualTypeArguments();

            if (typeArguments.length > 0) {
                var typeArgument = typeArguments[ENTITY_TYPE_ARG_IDX];
                return (Class<?>) typeArgument;
            }
        }
        throw new IllegalStateException("Cannot determine entity class");
    }
}
