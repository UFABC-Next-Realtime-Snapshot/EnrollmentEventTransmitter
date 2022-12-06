package org.ufabc.next.enrollmenteventtransmitter.infrastructure.student.rest;

import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.create.InputCreateStudent;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.enroll.InputEnrollStudent;
import org.ufabc.next.enrollmenteventtransmitter.util.Cleanable;
import org.ufabc.next.enrollmenteventtransmitter.util.CourseFixture;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class StudentResourceTest extends Cleanable {

    @Test
    public void shouldCreateNewStudent() {
        var course = CourseFixture.aCourse();

        var input = new InputCreateStudent("João", "112728281",course.name(), 3.5F, 1, 'M');
        given()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post("/student")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    public void shouldNotCreateStudentBecauseRequestIsInvalid() {
    }

    @Test
    public void shouldNotCreateStudentBecauseRequest() {
    }

    @Test
    public void shouldNotCreateStudentBecauseAlreadyExists() {
        var input = new InputCreateStudent(null, null, "BCC", 0, 0, 'M');

    }

}