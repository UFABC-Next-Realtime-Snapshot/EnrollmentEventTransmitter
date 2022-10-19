package org.ufabc.next.domain.student;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.ufabc.next.domain.Student.InvalidStudentException;
import org.ufabc.next.domain.Student.StudentFactory;
import org.ufabc.next.domain.commons.valueObjects.Shift;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class StudentFactoryTest {
    private final String name = "SomeName";
    private final String ra = "171626328";
    private final float validCrValue = 3;
    private final float validCpValue = 1;
    private final float invalidCrValue = -1;
    private final float invalidCpValue = -1;
    private final boolean withReservation = true;
    private final boolean noReservation = false;

    @Test
    public void shouldCreateMorningShiftStudent(){
        assertDoesNotThrow(() -> {
            var student = StudentFactory.MorningShiftStudent(name, ra, validCrValue, validCpValue, withReservation);
            assertEquals(name, student.name());
            assertEquals(ra, student.ra().value());
            assertEquals(validCpValue, student.cp().value());
            assertEquals(validCrValue, student.cr().value());
            assertEquals(withReservation, student.reservation());
            assertEquals(Shift.MORNING, student.shift());
        });
    }

    @Test
    public void shouldCreateNightShiftStudent(){
        assertDoesNotThrow(() -> {
            var student = StudentFactory.NightShiftStudent(name, ra, validCrValue, validCpValue, noReservation);
            assertEquals(name, student.name());
            assertEquals(ra, student.ra().value());
            assertEquals(validCpValue, student.cp().value());
            assertEquals(validCrValue, student.cr().value());
            assertEquals(noReservation, student.reservation());
            assertEquals(Shift.NIGHT, student.shift());
        });
    }

    @Test
    public void shouldntCreateMorningShiftStudent(){
        assertThrows(InvalidStudentException.class, () -> {
            StudentFactory.NightShiftStudent(name, ra, invalidCrValue, validCpValue, withReservation);
        });

        assertThrows(InvalidStudentException.class, () -> {
            StudentFactory.NightShiftStudent(name, ra, validCrValue, invalidCpValue, withReservation);
        });

        assertThrows(InvalidStudentException.class, () -> {
            StudentFactory.NightShiftStudent(name, ra, invalidCrValue, invalidCpValue, withReservation);
        });
    }

    @Test
    public void shouldntCreateNightShiftStudent(){
        assertThrows(InvalidStudentException.class,() -> {
            StudentFactory.NightShiftStudent(name, ra, invalidCrValue, validCpValue, noReservation);
        });

        assertThrows(InvalidStudentException.class, () -> {
            StudentFactory.NightShiftStudent(name, ra, validCrValue, invalidCpValue, noReservation);
        });

        assertThrows(InvalidStudentException.class, () -> {
            StudentFactory.NightShiftStudent(name, ra, invalidCrValue, invalidCpValue, noReservation);
        });
    }
}
