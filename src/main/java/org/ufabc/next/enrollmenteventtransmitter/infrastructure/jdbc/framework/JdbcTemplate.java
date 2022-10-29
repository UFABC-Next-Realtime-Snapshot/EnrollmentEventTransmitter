package org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc.framework;

import io.agroal.api.AgroalDataSource;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class JdbcTemplate {

    private static final Logger LOGGER = Logger.getLogger(JdbcTemplate.class);

    @Inject
    AgroalDataSource dataSource;

    public void update(String query, ExternalOperation externalOperation) {
        init((statement) -> {
            externalOperation.operateOn(statement);
            statement.execute();
        }, query);
    }

    public <T> T queryForObject(String query, ExternalOperation externalOperation, RowMapper<T> mapper) {
        AtomicReference<T> result = new AtomicReference<>();
        init((statement) -> {
            externalOperation.operateOn(statement);
            var resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return;
            }
            result.set(mapper.mapRow(resultSet));
            resultSet.close();
        }, query);
        return result.get();
    }

    public <T> List<T> queryForList(String query, ExternalOperation externalOperation, RowMapper<T> mapper) {
        AtomicReference<List<T>> result = new AtomicReference<>(new ArrayList<>());
        init((statement) -> {
            externalOperation.operateOn(statement);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.get().add(mapper.mapRow(resultSet));
            }
            resultSet.close();
        }, query);
        return result.get();
    }

    private void init(Connection connection, String query) {
        try (var conn = dataSource.getConnection();
             var statement = conn.prepareStatement(query)) {
            connection.operateOn(statement);
        } catch (SQLException exception) {
            LOGGER.error(String.format("Error in query %s", query), exception);
            throw new JdbcException(exception.getMessage());
        }
    }

    @FunctionalInterface
    public interface ExternalOperation {
        void operateOn(PreparedStatement statement) throws SQLException;
    }

    @FunctionalInterface
    public interface Connection {
        void operateOn(PreparedStatement statement) throws SQLException;
    }
}
