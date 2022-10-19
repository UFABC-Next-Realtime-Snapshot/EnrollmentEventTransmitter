package org.ufabc.next.domain.student;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
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
    
    @Test
    public void shouldCreateMorningShiftStudent(){
        assertDoesNotThrow(() -> {
            var student = StudentFactory.MorningShiftStudent(name, ra, validCrValue, validCpValue);
            assertEquals(name, student.name());
            assertEquals(ra, student.ra().value());
            assertEquals(validCpValue, student.cp().value());
            assertEquals(validCrValue, student.cr().value());
            assertEquals(Shift.MORNING, student.shift());
        });
    }

    @Test
    public void shouldCreateNightShiftStudent(){
        assertDoesNotThrow(() -> {
            var student = StudentFactory.NightShiftStudent(name, ra, validCrValue, validCpValue);
            assertEquals(name, student.name());
            assertEquals(ra, student.ra().value());
            assertEquals(validCpValue, student.cp().value());
            assertEquals(validCrValue, student.cr().value());
            assertEquals(Shift.NIGHT, student.shift());
        });
    }

    @Test
    public void shouldntCreateMorningShiftStudent(){
        assertThrows(InvalidStudent.class, () -> {
            StudentFactory.NightShiftStudent(name, ra, invalidCrValue, validCpValue);
        });

        assertThrows(InvalidStudent.class, () -> {
            StudentFactory.NightShiftStudent(name, ra, validCrValue, invalidCpValue);
        });
    }

    @Test
    public void shouldntCreateNightShiftStudent(){
        assertThrows(InvalidStudent.class,() -> {
            StudentFactory.NightShiftStudent(name, ra, invalidCrValue, validCpValue);
        });

        assertThrows(InvalidStudent.class, () -> {
            StudentFactory.NightShiftStudent(name, ra, validCrValue, invalidCpValue);
        });
    }
}
