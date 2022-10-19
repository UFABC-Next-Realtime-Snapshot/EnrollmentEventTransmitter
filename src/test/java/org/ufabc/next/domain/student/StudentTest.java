package org.ufabc.next.domain.student;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.ufabc.next.domain.Student.InvalidStudentException;
import org.ufabc.next.domain.Student.Student;
import org.ufabc.next.domain.commons.valueObjects.Cp;
import org.ufabc.next.domain.commons.valueObjects.Cr;
import org.ufabc.next.domain.commons.valueObjects.Ra;
import org.ufabc.next.domain.commons.valueObjects.Shift;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class StudentTest {
    private final String name = "SomeName";
    private final String ra = "171626328";
    private final float validCrValue = 3;
    private final float validCpValue = 1;
    private final float invalidCrValue = -1;
    private final float invalidCpValue = -1;
    private final boolean withReservation = true;
    private final boolean noReservation = false;

    @Test
    public void shouldCreateStudentWithRawValues() {
        assertDoesNotThrow(() -> {
            var student = new Student(name, ra, validCrValue, validCpValue, withReservation, Shift.MORNING);
            assertEquals(name, student.name());
            assertEquals(ra, student.ra().value());
            assertEquals(validCpValue, student.cp().value());
            assertEquals(validCrValue, student.cr().value());
            assertEquals(Shift.MORNING, student.shift());
        });
    }

    @Test
    public void shouldntCreateStudentWithRawValues() {
        assertThrows(InvalidStudentException.class, () -> {
            new Student(name, ra, invalidCrValue, validCpValue, withReservation, Shift.MORNING);
        });

        assertThrows(InvalidStudentException.class, () -> {
            new Student(name, ra, validCrValue, invalidCpValue, withReservation, Shift.MORNING);
        });

        assertThrows(InvalidStudentException.class, () -> {
            new Student(name, ra, invalidCrValue, invalidCpValue, withReservation, Shift.MORNING);
        });
    }

    @Test
    public void shouldCreateStudent() {
        assertDoesNotThrow(() -> {
            var student = new Student(name, new Ra(ra), new Cr(validCrValue), new Cp(validCpValue),
                    noReservation, Shift.NIGHT);
            assertEquals(name, student.name());
            assertEquals(ra, student.ra().value());
            assertEquals(validCpValue, student.cp().value());
            assertEquals(validCrValue, student.cr().value());
            assertEquals(noReservation, student.reservation());
            assertEquals(Shift.NIGHT, student.shift());
        });
    }
}
