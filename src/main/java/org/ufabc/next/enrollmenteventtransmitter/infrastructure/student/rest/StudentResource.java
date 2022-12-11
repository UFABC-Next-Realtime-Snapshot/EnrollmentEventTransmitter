package org.ufabc.next.enrollmenteventtransmitter.infrastructure.student.rest;

import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.create.CreateStudent;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.create.InputCreateStudent;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.enroll.EnrollStudent;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.enroll.InputEnrollStudent;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.find.FindStudentDisciplines;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.update.InputUpdateStudent;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.update.UpdateStudent;

import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/students/student")
public class StudentResource {

    private final CreateStudent createStudent;
    private final UpdateStudent updateStudent;
    private final EnrollStudent enrollStudent;
    private final FindStudentDisciplines findStudentDisciplines;

    public StudentResource(CreateStudent createStudent, UpdateStudent updateStudent, EnrollStudent enrollStudent, FindStudentDisciplines findStudentDisciplines) {
        this.createStudent = createStudent;
        this.updateStudent = updateStudent;
        this.enrollStudent = enrollStudent;
        this.findStudentDisciplines = findStudentDisciplines;
    }

    @POST
    public Response create(InputCreateStudent input) {
        createStudent.execute(input);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    public Response update(InputUpdateStudent input) {
        updateStudent.execute(input);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @POST
    @Path("/enroll")
    public Response enroll(InputEnrollStudent input) {
        enrollStudent.execute(input);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{ra}/disciplines")
    public Response getDisciplines(@PathParam("ra") String ra) {
        return Response
                .status(Response.Status.OK)
                .entity(findStudentDisciplines.execute(ra))
                .build();
    }
}
