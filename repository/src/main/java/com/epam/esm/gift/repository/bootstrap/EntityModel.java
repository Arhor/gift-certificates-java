package com.epam.esm.gift.repository.bootstrap;

import java.util.List;

public record EntityModel(String tableName, ColumnProperty idColumn, List<ColumnProperty> restColumns) {
}
