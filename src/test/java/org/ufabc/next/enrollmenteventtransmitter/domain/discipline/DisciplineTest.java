package org.ufabc.next.enrollmenteventtransmitter.domain.discipline;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cp;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class DisciplineTest {

    private final Course course = new Course(1L, "Course");

    @Test
    public void mustCreateDiscipline() {
        var discipline = Discipline.aDiscipline()
                .withId(1L)
                .withCode("aCode")
                .withCourse(course)
                .withName("aName")
                .withShift(Shift.MORNING)
                .withCR(new Cr(3))
                .withPracticeProfessor(new Professor("aPracticeProfessorPName"))
                .withTheoryProfessor(new Professor("aTheoryProfessorName"))
                .withVacancies((short) 10)
                .build();

        assertEquals("aCode", discipline.code());
        assertEquals(course, discipline.course());
        assertEquals("aName", discipline.name());
        assertEquals(Shift.MORNING, discipline.shift());
        assertEquals(0, discipline.thresholdCp().value());
        assertEquals(3, discipline.thresholdCr().value());
        assertEquals(new Professor("aPracticeProfessorPName"), discipline.practiceProfessor());
        assertEquals(new Professor("aTheoryProfessorName"), discipline.theoryProfessor());
        assertEquals(10, discipline.vacancies());
    }

    @Test
    public void whenCreateDisciplineWithInvalidCodeMustThrowIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> Discipline.aDiscipline()
                        .withId(1L)
                        .withCourse(course)
                        .withName("aName")
                        .withShift(Shift.MORNING)
                        .withCR(new Cr(1))
                        .withCP(new Cp(0.855F))
                        .withPracticeProfessor(new Professor("aPracticeProfessorPName"))
                        .withTheoryProfessor(new Professor("aTheoryProfessorName"))
                        .withVacancies((short) 10)
                        .build());

        String expectedMessage = "The parameter [code] cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenCreateDisciplineWithInvalidShiftMustThrowIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> Discipline.aDiscipline()
                        .withId(1L)
                        .withCourse(course)
                        .withCode("aCode")
                        .withName("aName")
                        .withCR(new Cr(4))
                        .withCP(new Cp(0))
                        .withPracticeProfessor(new Professor("aPracticeProfessorPName"))
                        .withTheoryProfessor(new Professor("aTheoryProfessorName"))
                        .withVacancies((short) 50)
                        .build());

        String expectedMessage = "The parameter [shift] cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenCreateDisciplineWithInvalidCourseMustThrowIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> Discipline.aDiscipline()
                        .withId(1L)
                        .withCode("aCode")
                        .withName("aName")
                        .withShift(Shift.MORNING)
                        .withCR(new Cr(1))
                        .withCP(new Cp(1))
                        .withPracticeProfessor(new Professor("aPracticeProfessorPName"))
                        .withTheoryProfessor(new Professor("aTheoryProfessorName"))
                        .withVacancies((short) 25)
                        .build());

        String expectedMessage = "The parameter [course] cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenCreateDisciplineWithInvalidNameMustThrowIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> Discipline.aDiscipline()
                        .withId(1L)
                        .withCourse(course)
                        .withCode("aCode")
                        .withShift(Shift.MORNING)
                        .withCR(new Cr(3))
                        .withCP(new Cp())
                        .withPracticeProfessor(new Professor("aPracticeProfessorPName"))
                        .withTheoryProfessor(new Professor("aTheoryProfessorName"))
                        .withVacancies((short) 10)
                        .build());

        String expectedMessage = "The parameter [name] cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
