package org.ufabc.next.enrollmenteventtransmitter.infrastructure.commons.rest;

import org.ufabc.next.enrollmenteventtransmitter.domain.student.InvalidStudentException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidStudentExceptionHandler extends ExceptionHandler
        implements ExceptionMapper<InvalidStudentException> {
    @Override
    public Response toResponse(InvalidStudentException exception) {
        var status = Response.Status.BAD_REQUEST.getStatusCode();
        var details = new ExceptionDetails("Invalid Student", status,
                exception.getMessage(),System.currentTimeMillis());
        return Response.status(status).entity(details).build();
    }
}
