package com.epam.esm.gift.repository.impl;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
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

            for (Map.Entry<ColumnProperty, Object> diff : diffs) {
                var column = diff.getKey();
                var value = diff.getValue();
                params.put(column.propName(), value);
            }

            jdbcTemplate.update(
                """
                    UPDATE certificates
                    SET ${diffs.joinToString { (column, _) -> "${column.realName} = :${column.propName}" }}
                    WHERE id = :certificateId
                    """,
                params
            );
        }
    }
}