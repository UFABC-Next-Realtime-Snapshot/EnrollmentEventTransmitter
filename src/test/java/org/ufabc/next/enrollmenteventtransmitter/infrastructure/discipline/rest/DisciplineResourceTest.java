package org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.rest;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Professor;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.rest.request.DisciplineRequest;
import org.ufabc.next.enrollmenteventtransmitter.util.Cleanable;
import org.ufabc.next.enrollmenteventtransmitter.util.CourseFixture;
import org.ufabc.next.enrollmenteventtransmitter.util.ProfessorFixture;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(DisciplineResource.class)
public class DisciplineResourceTest extends Cleanable {

    @Test
    public void whenProfessorsExistsMustCreateDiscipline() {
        Professor theoryProfessor = ProfessorFixture.theoryProfessor();
        Professor practicalProfessor = ProfessorFixture.practiceProfessor();
        Course course = CourseFixture.aCourse();

        given()
                .contentType(ContentType.JSON)
                .body(new DisciplineRequest("aNane", "aCode",
                        practicalProfessor.name(), theoryProfessor.name(), course.name(), 'M'))
                .when()
                .post("/discipline")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body(is(""));
    }

    @Test
    public void whenProfessorsNotExistsMustCreateDiscipline() {
        Course course = CourseFixture.aCourse();

        given()
                .contentType(ContentType.JSON)
                .body(new DisciplineRequest("aNane", "aCode",
                        "aProfessor", "anotherProfessor", course.name(), 'M'))
                .when()
                .post("/discipline")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body(is(""));
    }

    @Test
    public void whenPracticalProfessorNotExistAndTheoryProfessorIsNullMustCreateDiscipline() {
        Course course = CourseFixture.aCourse();

        given()
                .contentType(ContentType.JSON)
                .body(new DisciplineRequest("aNane", "aCode",
                        "aProfessor", null, course.name(), 'N'))
                .when()
                .post("/discipline")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body(is(""));
    }

    @Test
    public void whenPracticalProfessorIsNullAndTheoryProfessorNotExistMustCreateDiscipline() {
        Course course = CourseFixture.aCourse();

        given()
                .contentType(ContentType.JSON)
                .body(new DisciplineRequest("aNane", "aCode",
                        null, "aProfessor", course.name(), 'N'))
                .when()
                .post("/discipline")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body(is(""));
    }

    @Test
    public void whenTryCreateDisciplineWithNonexistentShiftShouldReturnBadRequest() {
        Course course = CourseFixture.aCourse();

        given()
                .contentType(ContentType.JSON)
                .body(new DisciplineRequest("aNane", "aCode",
                        "aProfessor", "anotherProfessor", course.name(),
                        'G'))
                .when()
                .post("/discipline")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("status", is(HttpStatus.SC_BAD_REQUEST))
                .body("details", is("Initial G Shift nonexistent"));
    }

    @Test
    public void whenTryCreateDisciplineWithNonexistentCourseShouldReturnBadRequest() {
        Professor theoryProfessor = ProfessorFixture.theoryProfessor();
        Professor practicalProfessor = ProfessorFixture.practiceProfessor();

        given()
                .contentType(ContentType.JSON)
                .body(new DisciplineRequest("aNane", "aCode",
                        practicalProfessor.name(), theoryProfessor.name(), "aCourse", 'M'))
                .when()
                .post("/discipline")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("status", is(HttpStatus.SC_NOT_FOUND))
                .body("details", is("Course aCourse not found"));
    }
}
