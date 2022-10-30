package org.ufabc.next.enrollmenteventtransmitter.domain.student;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class StudentBuilderTest {
    private final String name = "SomeName";
    private final String ra = "171626328";
    private final float validCrValue = 3;
    private final float validCpValue = 1;
    private final float invalidCrValue = -1;
    private final float invalidCpValue = -1;

    @Test
    public void shouldCreateStudent() {
        assertDoesNotThrow(() -> {
            var builder = new StudentBuilder(name, ra, Shift.NIGHT);
            var student = builder.withCr(validCrValue).withCp(validCpValue).build();
            assertEquals(name, student.name());
            assertEquals(ra, student.ra().value());
            assertEquals(validCpValue, student.cp().value());
            assertEquals(validCrValue, student.cr().value());
            assertEquals(Shift.NIGHT, student.shift());
        });

       
        assertDoesNotThrow(() -> {
            var builder = new StudentBuilder(name, ra, Shift.MORNING);
            var student = builder.build();
            assertEquals(name, student.name());
            assertEquals(ra, student.ra().value());
            assertEquals(0, student.cp().value());
            assertEquals(0, student.cr().value());
            assertEquals(Shift.MORNING, student.shift());
        });
    }

    @Test
    public void shouldntCreateStudent() {
        assertThrows(InvalidStudentException.class, () -> {
            new StudentBuilder(name, ra, Shift.MORNING).withCr(invalidCrValue).withCp(validCpValue).build();
        });

        assertThrows(InvalidStudentException.class, () -> {
            new StudentBuilder(name, ra, Shift.MORNING).withCr(validCrValue).withCp(invalidCpValue).build();
        });

        assertThrows(InvalidStudentException.class, () -> {
            new StudentBuilder(name, ra, Shift.MORNING).withCr(invalidCrValue).withCp(invalidCpValue).build();
        });
    }
}
