package com.epam.esm.gift.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.epam.esm.gift.model.Certificate;
import com.epam.esm.gift.repository.CertificateRepository;
import com.epam.esm.gift.repository.bootstrap.ColumnProperty;

@Repository
public class CertificateRepositoryImpl extends AbstractRepository<Certificate, Long> implements CertificateRepository {

    public CertificateRepositoryImpl(final RowMapper<Certificate> rowMapper) {
        super(rowMapper);
    }

    @Override
    public void updatePartially(final Long certificateId, final List<Map.Entry<ColumnProperty, Object>> diffs) {
        if (!diffs.isEmpty()) {

            for (Map.Entry<ColumnProperty, Object> diff : diffs) {
                var column = diff.getKey();
                if (!entityModel.restColumns().contains(column)) {
                    throw new IllegalArgumentException(
                        "Property " + column.propName() + " does not belong to the Certificate entity"
                    );
                }
                if (entityModel.idColumn() == column) {
                    throw new IllegalArgumentException(
                        "It is forbidden to modify ID property: " + column.propName()
                    );
                }
            }


            var params = new HashMap<String, Object>();
            params.put("certificateId", certificateId);

            for (var diff : diffs) {
                var column = diff.getKey();
                var value = diff.getValue();
                params.put(column.propName(), value);
            }

            var setExpression = diffs.stream()
                .map(Map.Entry::getKey)
                .map(it -> it.realName() + " = :" + it.propName())
                .collect(Collectors.joining(", "));

            jdbcTemplate.update(
                "UPDATE certificates SET " + setExpression + " WHERE id = :certificateId",
                params
            );
        }
    }
}