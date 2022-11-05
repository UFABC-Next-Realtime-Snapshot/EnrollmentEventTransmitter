package org.ufabc.next.enrollmenteventtransmitter.infrastructure.student.rest;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.create.CreateStudent;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.create.InputCreateStudent;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.enroll.EnrollStudent;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.update.InputUpdateStudent;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.update.UpdateStudent;

@Path("/student")
public class StudentResource {

    @Inject
    CreateStudent createStudent;

    @Inject
    UpdateStudent updateStudent;

    @Inject
    EnrollStudent enrollStudent;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String create(InputCreateStudent input) {
        try{
           createStudent.execute(input);
           return "Deu bom criei o estudante";
        } catch(Exception e){
            return "Nao deu bom";
        }
    }

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public String update(InputUpdateStudent input) {
        try{
            updateStudent.execute(input);
            return "Deu bom criei o estudante";
         } catch(Exception e){
             return "Nao deu bom";
         }
    }

}
