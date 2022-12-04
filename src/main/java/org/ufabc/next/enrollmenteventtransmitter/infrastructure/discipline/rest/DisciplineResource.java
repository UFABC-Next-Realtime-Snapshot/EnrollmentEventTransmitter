package org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.rest;

import org.ufabc.next.enrollmenteventtransmitter.application.discipline.usecase.CreateDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Discipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Professor;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.rest.request.DisciplineRequest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/disciplines")
public class DisciplineResource {
    private final CreateDiscipline createDiscipline;

    public DisciplineResource(CreateDiscipline createDiscipline) {
        this.createDiscipline = createDiscipline;
    }

    @POST
    @Path("/discipline")
    public Response create(DisciplineRequest request) {
        createDiscipline.execute(getDiscipline(request));
        return Response.status(Response.Status.CREATED).build();
    }

    private IDiscipline getDiscipline(DisciplineRequest request) {
        return Discipline.aDiscipline()
                .withCode(request.code())
                .withName(request.name())
                .withTheoryProfessor(request.theoryProfessor == null ? null : new Professor(request.theoryProfessor()))
                .withPracticeProfessor(request.practicalProfessor == null ? null : new Professor(request.practicalProfessor()))
                .withCourse(new Course(request.course()))
                .withShift(Shift.fromInitial(request.shift()))
                .withVacancies(request.vacancies())
                .build();
    }
}
