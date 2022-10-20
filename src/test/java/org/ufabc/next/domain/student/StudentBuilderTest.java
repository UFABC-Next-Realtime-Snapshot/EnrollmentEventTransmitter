package org.ufabc.next.domain.student;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.ufabc.next.domain.commons.valueObjects.Shift;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class StudentBuilderTest {
    private final String name = "SomeName";
    private final String ra = "171626328";
    private final float validCrValue = 3;
    private final float validCpValue = 1;
    private final float invalidCrValue = -1;
    private final float invalidCpValue = -1;
    private final boolean withReservation = true;
    private final boolean noReservation = false;

    @Test
    public void shouldCreateStudent() {
        assertDoesNotThrow(() -> {
            var builder = new StudentBuilder(name, ra, Shift.NIGHT);
            var student = builder.WithCr(validCrValue).WithCp(validCpValue).build();
            assertEquals(name, student.name());
            assertEquals(ra, student.ra().value());
            assertEquals(validCpValue, student.cp().value());
            assertEquals(validCrValue, student.cr().value());
            assertEquals(noReservation, student.reservation());
            assertEquals(Shift.NIGHT, student.shift());
        });

       
        assertDoesNotThrow(() -> {
            var builder = new StudentBuilder(name, ra, Shift.MORNING);
            var student = builder.WithReservation().build();
            assertEquals(name, student.name());
            assertEquals(ra, student.ra().value());
            assertEquals(0, student.cp().value());
            assertEquals(0, student.cr().value());
            assertEquals(withReservation, student.reservation());
            assertEquals(Shift.MORNING, student.shift());
        });
    }

    @Test
    public void shouldntCreateStudent() {
        assertThrows(InvalidStudentException.class, () -> {
            new StudentBuilder(name, ra, Shift.MORNING).WithCr(invalidCrValue).WithCp(validCpValue).build();
        });

        assertThrows(InvalidStudentException.class, () -> {
            new StudentBuilder(name, ra, Shift.MORNING).WithCr(validCrValue).WithCp(invalidCpValue).build();
        });

        assertThrows(InvalidStudentException.class, () -> {
            new StudentBuilder(name, ra, Shift.MORNING).WithCr(invalidCrValue).WithCp(invalidCpValue).build();
        });
    }
}
