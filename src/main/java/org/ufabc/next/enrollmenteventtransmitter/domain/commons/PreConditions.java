package org.ufabc.next.enrollmenteventtransmitter.domain.commons;


public class PreConditions {

    private PreConditions() {
        // static class
    }

    public static <T> T checkNotNull(T input, String paramName) {
        if (input == null) {
            throw new IllegalArgumentException(String.format("The parameter [%s] cannot be null", paramName));
        }
        return input;
    }

    public static <T extends Exception> void checkArgument(boolean expression, T exception) throws T {
        if (expression) {
            throw exception;
        }
    }
}