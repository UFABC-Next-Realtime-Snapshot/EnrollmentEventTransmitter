package org.ufabc.next.enrollmenteventtransmitter.controllers;

import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.Form;
import org.ufabc.next.enrollmenteventtransmitter.application.AddNewStudentCommand;
import org.ufabc.next.enrollmenteventtransmitter.application.AddNewStudentCommandHandler;

@Path("/student")
public class StudentController {
    private final AddNewStudentCommandHandler addNewStudentCommandHandler;

    public StudentController(AddNewStudentCommandHandler addNewStudentCommandHandler) {
        this.addNewStudentCommandHandler = addNewStudentCommandHandler;
    }

    // /**
    //  * @param id
    //  */
    // @GET
    // void getById(@QueryParam String id) {
    // }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{id}")
    public Response getStudentByID(@PathParam("id") String id ) {
        return Response.status(200).
            entity("id: " + id).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addStudent(@FormParam("name") String name) {
        AddNewStudentCommand command = new AddNewStudentCommand(name);
        addNewStudentCommandHandler.handle(command);
        return Response.status(200).build();
    }
}