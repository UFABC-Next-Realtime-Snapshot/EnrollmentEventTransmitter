package org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc.framework;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FakeEntityRowMapper implements RowMapper<FakeEntity> {
    @Override
    public FakeEntity mapRow(ResultSet resultSet) throws SQLException {
        return new FakeEntity(
                resultSet.getLong("id"),
                resultSet.getString("data"),
                resultSet.getString("another_data"),
                resultSet.getInt("fake_data")
        );
    }
}
