package org.ufabc.next.enrollmenteventtransmitter.infrastructure.commons.rest;

public abstract class ExceptionHandler {
    protected static class ExceptionDetails {
        public final String title;
        public final int status;
        public final String details;
        public final long timestamp;

        public ExceptionDetails(String title, int status, String details, long timestamp) {
            this.title = title;
            this.status = status;
            this.details = details;
            this.timestamp = timestamp;
        }
    }
}
