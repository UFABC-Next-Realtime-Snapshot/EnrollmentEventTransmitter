package org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc.framework;

public class TransactionOperation {
    private final String query;
    private final JdbcTemplate.Operation operation;

    public TransactionOperation(String query, JdbcTemplate.Operation operation) {
        this.query = query;
        this.operation = operation;
    }

    public String query() {
        return query;
    }

    public JdbcTemplate.Operation operation() {
        return operation;
    }
}
