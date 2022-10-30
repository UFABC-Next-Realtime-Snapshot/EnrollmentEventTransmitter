package org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc.framework;

public class TransactionException extends JdbcException {
    public TransactionException(String message) {
        super(message);
    }
}
