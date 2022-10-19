package org.ufabc.next.domain.student;

import org.ufabc.next.domain.commons.valueObjects.Shift;

public class StudentFactory {
    public static IStudent MorningShiftStudent(String name, String ra, float cr, float cp) throws InvalidStudent{
        return new Student(name, ra, cr, cp, Shift.MORNING);
    }

    public static IStudent NightShiftStudent(String name, String ra, float cr, float cp) throws InvalidStudent{
        return new Student(name, ra, cr, cp, Shift.NIGHT);
    }
}
