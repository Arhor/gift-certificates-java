package com.epam.esm.gift.repository.bootstrap;

import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class QueryProvider {

    private static final String SELECT_ALL_TEMPLATE = "SELECT %s FROM %s";
    private static final String SELECT_ONE_TEMPLATE = "SELECT %s FROM %s WHERE %s";
    private static final String DELETE_ONE_TEMPLATE = "DELETE FROM %s WHERE %s";
    private static final String INSERT_ONE_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s)";
    private static final String UPDATE_ONE_TEMPLATE = "UPDATE %s SET %s WHERE %s";

    public Queries buildQueries(final EntityModel entityModel) {

        var tableName = entityModel.tableName();
        var idColumn = entityModel.idColumn();
        var restColumns = entityModel.restColumns();

        var restColumnsArray = restColumns.toArray(ColumnProperty[]::new);

        var allColumns = Stream.concat(Stream.of(idColumn), Stream.of(restColumnsArray))
            .map(ColumnProperty::realName)
            .collect(joining(", "));

        var restColsRealNames = joinToString(restColumns, ColumnProperty::realName);
        var restColsPropNames = joinToString(restColumns, col -> ":" + col.propName());

        var idColToIdProp = colToProp(idColumn);
        var colsToProps = colsToProps(restColumnsArray);

        return new Queries(
            SELECT_ALL_TEMPLATE.formatted(allColumns, tableName),
            SELECT_ONE_TEMPLATE.formatted(allColumns, tableName, idColToIdProp),
            DELETE_ONE_TEMPLATE.formatted(tableName, idColToIdProp),
            INSERT_ONE_TEMPLATE.formatted(tableName, restColsRealNames, restColsPropNames),
            UPDATE_ONE_TEMPLATE.formatted(tableName, colsToProps, idColToIdProp)
        );
    }

    private String colsToProps(final ColumnProperty... columns) {
        return joinToString(List.of(columns), this::colToProp);
    }

    private String colToProp(final ColumnProperty column) {
        return column.realName() + " = :" + column.propName();
    }

    private static final String DEFAULT_DELIMITER = ", ";

    private String joinToString(final Iterable<?> items) {
        return joinToString(items, DEFAULT_DELIMITER, String::valueOf);
    }

    private String joinToString(final Iterable<?> items, final String delimiter) {
        return joinToString(items, delimiter, String::valueOf);
    }

    private <T> String joinToString(final Iterable<T> items, final Function<T, String> mapper) {
        return joinToString(items, DEFAULT_DELIMITER, mapper);
    }

    private <T> String joinToString(final Iterable<T> items, final String delimiter, final Function<T, String> mapper) {
        var result = new StringJoiner(delimiter);
        for (var item : items) {
            result.add(mapper.apply(item));
        }
        return result.toString();
    }
}