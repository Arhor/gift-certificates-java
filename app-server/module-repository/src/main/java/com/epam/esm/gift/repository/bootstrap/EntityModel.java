package com.epam.esm.gift.repository.bootstrap;

import java.util.List;

public class EntityModel {

    private final String tableName;
    private final ColumnProperty idColumn;
    private final List<ColumnProperty> restColumns;

    public EntityModel(String tableName, ColumnProperty idColumn, List<ColumnProperty> restColumns) {
        this.tableName = tableName;
        this.idColumn = idColumn;
        this.restColumns = restColumns;
    }

    public String tableName() {
        return tableName;
    }

    public ColumnProperty idColumn() {
        return idColumn;
    }

    public List<ColumnProperty> restColumns() {
        return restColumns;
    }
}
