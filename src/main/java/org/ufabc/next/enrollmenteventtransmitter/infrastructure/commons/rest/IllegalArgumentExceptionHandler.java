package org.ufabc.next.enrollmenteventtransmitter.infrastructure.commons.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionHandler extends ExceptionHandler
        implements ExceptionMapper<IllegalArgumentException> {
    @Override
    public Response toResponse(IllegalArgumentException exception) {
        var status = Response.Status.BAD_REQUEST.getStatusCode();
        var details = new ExceptionDetails("Illegal Argument", status,
                exception.getMessage(), System.currentTimeMillis());
        return Response.status(status).entity(details).build();
    }
}
