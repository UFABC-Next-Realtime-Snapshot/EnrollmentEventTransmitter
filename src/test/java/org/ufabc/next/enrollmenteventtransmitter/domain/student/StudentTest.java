package org.ufabc.next.enrollmenteventtransmitter.domain.student;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;

import io.quarkus.test.junit.QuarkusTest;

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
    public void shouldntCreateStudent() {
        assertThrows(InvalidStudentException.class, () -> {
            new Student(name, ra, invalidCrValue, validCpValue, Course.BCT, Shift.MORNING);
        });

        assertThrows(InvalidStudentException.class, () -> {
            new Student(name, ra, validCrValue, invalidCpValue, Course.BCT, Shift.MORNING);
        });

        assertThrows(InvalidStudentException.class, () -> {
            new Student(name, ra, invalidCrValue, invalidCpValue, Course.BCT, Shift.MORNING);
        });
    }
}
