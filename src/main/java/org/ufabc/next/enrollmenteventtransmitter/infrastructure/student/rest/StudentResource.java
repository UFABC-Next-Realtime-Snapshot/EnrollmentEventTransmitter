package org.ufabc.next.enrollmenteventtransmitter.infrastructure.student.rest;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.create.CreateStudent;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.create.InputCreateStudent;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.enroll.EnrollStudent;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.enroll.InputEnrollStudent;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.update.InputUpdateStudent;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.update.UpdateStudent;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.commons.rest.CustomExceptionHandler;

@Path("/student")
public class StudentResource {

    @Inject
    CreateStudent createStudent;

    @Inject
    UpdateStudent updateStudent;

    @Inject
    EnrollStudent enrollStudent;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(InputCreateStudent input) {
        createStudent.execute(input);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(InputUpdateStudent input) {
        updateStudent.execute(input);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @POST
    @Path("/enroll")
    @Produces(MediaType.TEXT_PLAIN)
    public Response enroll(InputEnrollStudent input){
        enrollStudent.execute(input);
        return Response.status(Response.Status.CREATED).build();
    }
}
