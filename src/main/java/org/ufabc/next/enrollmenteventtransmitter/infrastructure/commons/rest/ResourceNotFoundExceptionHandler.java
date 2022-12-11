package org.ufabc.next.enrollmenteventtransmitter.infrastructure.commons.rest;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.exceptions.ResourceNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ResourceNotFoundExceptionHandler extends ExceptionHandler
        implements ExceptionMapper<ResourceNotFoundException> {
    @Override
    public Response toResponse(ResourceNotFoundException exception) {
        var status = Response.Status.NOT_FOUND.getStatusCode();
        var details = new ExceptionDetails("Resource Not Found", status,
                exception.getMessage(), System.currentTimeMillis());
        return Response.status(status).entity(details).build();
    }
}
