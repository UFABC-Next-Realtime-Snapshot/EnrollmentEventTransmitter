package org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc.framework;

public class JdbcException extends RuntimeException {
    public JdbcException(String message) {
        super(message);
    }
}
