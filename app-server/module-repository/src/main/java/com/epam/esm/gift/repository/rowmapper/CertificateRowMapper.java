package com.epam.esm.gift.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.epam.esm.gift.repository.model.Certificate;

@Component
public class CertificateRowMapper implements RowMapper<Certificate> {

    @Override
    public Certificate mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        var certificate = new Certificate();

        certificate.setId(rs.getLong(Certificate.COL_ID));
        certificate.setName(rs.getString(Certificate.COL_NAME));
        certificate.setDescription(rs.getString(Certificate.COL_DESCRIPTION));
        certificate.setPrice(rs.getBigDecimal(Certificate.COL_PRICE));
        certificate.setDuration(rs.getInt(Certificate.COL_DURATION));
        certificate.setDateTimeCreated(asLocalDateTime(rs.getTimestamp(Certificate.COL_DATE_TIME_CREATED)));
        certificate.setDateTimeUpdated(asLocalDateTime(rs.getTimestamp(Certificate.COL_DATE_TIME_UPDATED)));

        return certificate;
    }

    private LocalDateTime asLocalDateTime(final Timestamp timestamp) {
        return (timestamp != null) ? timestamp.toLocalDateTime() : null;
    }
}
