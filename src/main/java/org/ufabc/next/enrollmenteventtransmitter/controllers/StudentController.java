package org.ufabc.next.enrollmenteventtransmitter.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.ufabc.next.enrollmenteventtransmitter.application.commands.AddNewStudentCommand;
import org.ufabc.next.enrollmenteventtransmitter.application.commands.AddNewStudentCommandHandler;
import org.ufabc.next.enrollmenteventtransmitter.application.commands.GetStudentByIDCommand;
import org.ufabc.next.enrollmenteventtransmitter.application.commands.GetStudentByIDCommandHandler;
import org.ufabc.next.enrollmenteventtransmitter.application.commands.RemoveStudentCommand;
import org.ufabc.next.enrollmenteventtransmitter.application.commands.RemoveStudentCommandHandler;

@Path("/student")
@RequestScoped
public class StudentController {
    @Inject private final GetStudentByIDCommandHandler _getStudentByIDCommandHandler;
    @Inject private final AddNewStudentCommandHandler _addNewStudentCommandHandler;
    @Inject private final RemoveStudentCommandHandler _removeStudentCommandHandler;

    public StudentController(
        GetStudentByIDCommandHandler getStudentByIdCommandHandler,
        AddNewStudentCommandHandler addNewStudentCommandHandler,
        RemoveStudentCommandHandler removeStudentCommandHandler
    ) {
        _getStudentByIDCommandHandler = getStudentByIdCommandHandler;
        _addNewStudentCommandHandler = addNewStudentCommandHandler;
        _removeStudentCommandHandler = removeStudentCommandHandler;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{id}")
    public Response getStudentByID(@PathParam("id") String id ) {
        GetStudentByIDCommand command = new GetStudentByIDCommand(id);
        _getStudentByIDCommandHandler.handle(command);
        return Response.status(200).
            entity("id: " + id).build();
    }

    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/{id}")
    public Response removeStudentByID(@PathParam("id") String id) {
        RemoveStudentCommand command = new RemoveStudentCommand(id);
        _removeStudentCommandHandler.handle(command);
        return Response.status(200).
            entity(id).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addStudent(@FormParam("name") String name) {
        AddNewStudentCommand command = new AddNewStudentCommand(name);
        _addNewStudentCommandHandler.handle(command);
        return Response.status(200).build();
    }
}