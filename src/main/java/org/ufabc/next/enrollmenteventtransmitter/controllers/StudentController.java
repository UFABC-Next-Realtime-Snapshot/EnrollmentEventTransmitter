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
import org.ufabc.next.enrollmenteventtransmitter.application.commands.GetStudentByRACommand;
import org.ufabc.next.enrollmenteventtransmitter.application.commands.GetStudentByRACommandHandler;
import org.ufabc.next.enrollmenteventtransmitter.application.commands.RemoveStudentByRACommand;
import org.ufabc.next.enrollmenteventtransmitter.application.commands.RemoveStudentByRACommandHandler;

@Path("/student")
@RequestScoped
public class StudentController {
    @Inject private final GetStudentByRACommandHandler _getStudentByRACommandHandler;
    @Inject private final AddNewStudentCommandHandler _addNewStudentCommandHandler;
    @Inject private final RemoveStudentByRACommandHandler _removeStudentCommandHandler;

    public StudentController(
        GetStudentByRACommandHandler getStudentByIdCommandHandler,
        AddNewStudentCommandHandler addNewStudentCommandHandler,
        RemoveStudentByRACommandHandler removeStudentCommandHandler
    ) {
        _getStudentByRACommandHandler = getStudentByIdCommandHandler;
        _addNewStudentCommandHandler = addNewStudentCommandHandler;
        _removeStudentCommandHandler = removeStudentCommandHandler;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{ra}")
    public Response getStudentByRA(@PathParam("ra") String ra ) {
        GetStudentByRACommand command = new GetStudentByRACommand(ra);
        return Response.status(200)
            .entity(_getStudentByRACommandHandler.handle(command))
            .build();
    }

    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/{ra}")
    public Response removeStudentByRA(@PathParam("ra") String ra) {
        RemoveStudentByRACommand command = new RemoveStudentByRACommand(ra);
        _removeStudentCommandHandler.handle(command);
        return Response.status(200).
            entity(ra).build();
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