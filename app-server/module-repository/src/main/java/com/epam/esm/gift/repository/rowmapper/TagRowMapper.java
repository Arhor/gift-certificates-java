package com.epam.esm.gift.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.epam.esm.gift.model.Tag;

@Component
public class TagRowMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        var tag = new Tag();

        tag.setId(rs.getLong(Tag.COL_ID));
        tag.setName(rs.getString(Tag.COL_NAME));

        return tag;
    }
}