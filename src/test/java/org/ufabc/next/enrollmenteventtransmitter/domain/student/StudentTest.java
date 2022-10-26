package org.ufabc.next.enrollmenteventtransmitter.domain.student;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class StudentTest {
    private final String name = "SomeName";
    private final String ra = "171626328";
    private final float validCrValue = 3;
    private final float validCpValue = 1;
    private final float invalidCrValue = -1;
    private final float invalidCpValue = -1;

    @Test
    public void shouldCreateStudent() {
        assertDoesNotThrow(() -> {
            var student = new Student(name, ra, validCrValue, validCpValue, Course.BCH, Shift.NIGHT);
            assertEquals(name, student.name());
            assertEquals(ra, student.ra().value());
            assertEquals(validCpValue, student.cp().value());
            assertEquals(validCrValue, student.cr().value());
            assertEquals(Shift.NIGHT, student.shift());
            assertEquals(Course.BCH, student.course());
        });

        assertDoesNotThrow(() -> {
            var student = new Student(name, ra, validCrValue, validCpValue, Course.BCT, Shift.MORNING);
            assertEquals(name, student.name());
            assertEquals(ra, student.ra().value());
            assertEquals(validCpValue, student.cp().value());
            assertEquals(validCrValue, student.cr().value());
            assertEquals(Shift.MORNING, student.shift());
            assertEquals(Course.BCT, student.course());
        });
    }

    @Test
    public void whenCreateStudentWithNegativeCrMustThrowInvalidStudentException() {
        Exception exception = assertThrows(InvalidStudentException.class,
                () -> new Student(name, ra, invalidCrValue, validCpValue, Course.BCT, Shift.MORNING));

        String expectedMessage = "CR: negative value";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenCreateStudentWithCrGreaterFourMustThrowInvalidStudentException() {
        Exception exception = assertThrows(InvalidStudentException.class,
                () -> new Student(name, ra, 5, validCpValue, Course.BCT, Shift.MORNING));

        String expectedMessage = "CR: value greater than 4";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenCreateStudentWithNegativeCpMustThrowInvalidStudentException() {
        Exception exception = assertThrows(InvalidStudentException.class,
                () -> new Student(name, ra, validCrValue, invalidCpValue, Course.BCT, Shift.MORNING));

        String expectedMessage = "cp: invalid value, must be greater 0";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenCreateStudentWithCpGreaterOneMustThrowInvalidStudentException() {
        Exception exception = assertThrows(InvalidStudentException.class,
                () -> new Student(name, ra, validCrValue, 7, Course.BCT, Shift.MORNING));

        String expectedMessage = "cp: invalid value, must be lower 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void whenCreateStudentWithInvalidCrAndInvalidCpMustThrowInvalidStudentException() {
        Exception exception = assertThrows(InvalidStudentException.class,
                () -> new Student(name, ra, invalidCrValue, invalidCpValue, Course.BCT, Shift.MORNING));

        String expectedMessage = "CR: negative value";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
