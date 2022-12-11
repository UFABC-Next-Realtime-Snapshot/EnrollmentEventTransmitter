package org.ufabc.next.enrollmenteventtransmitter.domain.student;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class StudentTest {
    private final Long id = 1L;
    private final String name = "SomeName";
    private final String ra = "171626328";
    private final Course course = new Course(1L, "Course");
    private final float validCrValue = 3;
    private final float validCpValue = 1;
    private final float invalidCrValue = -1;
    private final float invalidCpValue = -1;

    @Test
    public void shouldCreateStudent() {
        assertDoesNotThrow(() -> {
            var student = new Student(id, name, ra, validCrValue, validCpValue, course, Shift.NIGHT, List.of());
            assertEquals(name, student.name());
            assertEquals(ra, student.ra().value());
            assertEquals(validCpValue, student.cp().value());
            assertEquals(validCrValue, student.cr().value());
            assertEquals(Shift.NIGHT, student.shift());
            assertEquals(course, student.course());
        });

        assertDoesNotThrow(() -> {
            var student = new Student(id, name, ra, validCrValue, validCpValue, course, Shift.MORNING, List.of());
            assertEquals(name, student.name());
            assertEquals(ra, student.ra().value());
            assertEquals(validCpValue, student.cp().value());
            assertEquals(validCrValue, student.cr().value());
            assertEquals(Shift.MORNING, student.shift());
            assertEquals(course, student.course());
        });
    }

    @Test
    public void whenCreateStudentWithNegativeCrMustThrowInvalidStudentException() {
        Exception exception = assertThrows(InvalidStudentException.class,
                () -> new Student(id, name, ra, invalidCrValue, validCpValue, course, Shift.MORNING, List.of()));

        String expectedMessage = "CR: negative value";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenCreateStudentWithCrGreaterFourMustThrowInvalidStudentException() {
        Exception exception = assertThrows(InvalidStudentException.class,
                () -> new Student(id, name, ra, 5, validCpValue, course, Shift.MORNING, List.of()));

        String expectedMessage = "CR: value greater than 4";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenCreateStudentWithNegativeCpMustThrowInvalidStudentException() {
        Exception exception = assertThrows(InvalidStudentException.class,
                () -> new Student(id, name, ra, validCrValue, invalidCpValue, course, Shift.MORNING, List.of()));

        String expectedMessage = "cp: invalid value, must be greater 0";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenCreateStudentWithCpGreaterOneMustThrowInvalidStudentException() {
        Exception exception = assertThrows(InvalidStudentException.class,
                () -> new Student(id, name, ra, validCrValue, 7, course, Shift.MORNING, List.of()));

        String expectedMessage = "cp: invalid value, must be lower 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void whenCreateStudentWithInvalidCrAndInvalidCpMustThrowInvalidStudentException() {
        Exception exception = assertThrows(InvalidStudentException.class,
                () -> new Student(id, name, ra, invalidCrValue, invalidCpValue, course, Shift.MORNING, List.of()));

        String expectedMessage = "CR: negative value";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenCourseIsSameOfStudentMustReturnTrue() {
        var student = new Student(id, name, ra, validCrValue, validCpValue, course, Shift.MORNING, List.of());

        assertTrue(student.reservation(course));
    }

    @Test
    public void whenCourseIsNotSameOfStudentMustReturnFalse() {
        var student = new Student(id, name, ra, validCrValue, validCpValue, course, Shift.MORNING, List.of());

        assertFalse(student.reservation(new Course(null, "differentCourse")));
    }
}
