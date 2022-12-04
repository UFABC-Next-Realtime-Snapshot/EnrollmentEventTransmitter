package org.ufabc.next.enrollmenteventtransmitter.infrastructure.commons.rest;

import org.jboss.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomExceptionHandler extends ExceptionHandler
        implements ExceptionMapper<Exception> {

    private static final Logger LOGGER = Logger.getLogger(CustomExceptionHandler.class);

    @Override
    public Response toResponse(Exception exception) {
        var status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        LOGGER.error(exception.getMessage());
        var details = new ExceptionDetails("Application Error", status,
                "Application error!", System.currentTimeMillis());
        return Response.status(status).entity(details).build();
    }
}
