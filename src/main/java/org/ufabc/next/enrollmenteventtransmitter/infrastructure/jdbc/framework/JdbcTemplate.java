package org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc.framework;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class JdbcTemplate {

    private static final Logger LOGGER = Logger.getLogger(JdbcTemplate.class);

    @Inject
    DataSource dataSource;

    public void update(String query, Operation externalOperation) {
        init((statement) -> {
            externalOperation.operateOn(statement);
            statement.execute();
        }, query);
    }

    public <T> T queryForObject(String query, Operation externalOperation, RowMapper<T> mapper) {
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

    public <T> List<T> queryForList(String query, Operation externalOperation, RowMapper<T> mapper) {
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

    private void init(Operation internalOperation, String query) {
        try (var conn = dataSource.getConnection();
             var statement = conn.prepareStatement(query)) {
            internalOperation.operateOn(statement);
        } catch (SQLException exception) {
            LOGGER.error(String.format("Error in query %s", query), exception);
            throw new JdbcException(exception.getMessage());
        }
    }

    public void transactionUpdates(TransactionOperation... transactionOperations) {
        try {
            var conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            try {
                Arrays.stream(transactionOperations).forEach(transactionOperation -> {
                    try (var statement = conn.prepareStatement(transactionOperation.query())) {
                        transactionOperation.operation().operateOn(statement);
                        statement.execute();
                    } catch (SQLException exception) {
                        LOGGER.error(String.format("Error in query %s", transactionOperation.query()), exception);
                        throw new TransactionException(exception.getMessage());
                    }
                });
                conn.commit();
            } catch (TransactionException exception) {
                LOGGER.error("rollback init");
                conn.rollback();
                LOGGER.error("rollback finalize");
                throw new JdbcException(exception.getMessage());
            }
        } catch (SQLException exception) {
            LOGGER.error("Error in connection", exception);
            throw new JdbcException(exception.getMessage());
        }
    }

    @FunctionalInterface
    public interface Operation {
        void operateOn(PreparedStatement statement) throws SQLException;
    }
}
