package org.ufabc.next.enrollmenteventtransmitter.infrastructure.student.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.create.InputCreateStudent;
import org.ufabc.next.enrollmenteventtransmitter.util.*;

import javax.ws.rs.core.Response;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class StudentResourceTest extends Cleanable {

    @Test
    public void shouldCreateNewStudent() {
        var course = CourseFixture.aCourse();
        var input = new InputCreateStudent("João", "112728281", course.name(), 3.5F, 1, 'M');

        given()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post("/students/student")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    public void whenStudentIsInvalidShouldNoCreateStudent() {
        var course = CourseFixture.aCourse();
        var input = new InputCreateStudent("João", "112728281", course.name(), 3.5F, -1, 'M');

        given()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post("/students/student")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
                .body("details", is("cp: invalid value, must be greater 0"));
    }

    @Test
    public void shouldReturnStudentDisciplines() {
        var course = CourseFixture.aCourse();
        var practiceProfessor = ProfessorFixture.practiceProfessor();
        var theoryProfessor = ProfessorFixture.theoryProfessor();
        var discipline = DisciplineFixture.aDiscipline(course, practiceProfessor, theoryProfessor);
        var student = StudentFixture.aStudent(course, List.of(discipline));

        String response = "[{\"code\":\"Some code\",\"course\":\"aCourse\",\"name\":\"Some discipline\",\"practiceProfessor\":\"aPracticeProfessorName\",\"shift\":\"M\",\"subscribers\":0,\"theoryProfessor\":\"aTheoryProfessorName\",\"thresholdCp\":1.0,\"thresholdCr\":3.4,\"vacancies\":10}]";

        given()
                .when()
                .get(String.format("/students/student/%s/disciplines", student.ra().value()))
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(is(response));
    }

    @Test
    public void shouldNotReturnStudentDisciplines() {
        given()
                .when()
                .get("/students/student/nonexistentRA/disciplines")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode())
                .body("details", is("student not found"));
    }
}