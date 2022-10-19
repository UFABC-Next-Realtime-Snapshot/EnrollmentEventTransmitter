package org.ufabc.next.domain.Student;

import org.ufabc.next.domain.commons.valueObjects.Shift;

public class StudentFactory {
    public static IStudent MorningShiftStudent(String name, String ra, float cr, float cp, boolean reservation) throws InvalidStudentException{
        return new Student(name, ra, cr, cp, reservation, Shift.MORNING);
    }

    public static IStudent NightShiftStudent(String name, String ra, float cr, float cp, boolean reservation) throws InvalidStudentException{
        return new Student(name, ra, cr, cp, reservation, Shift.NIGHT);
    }
}
