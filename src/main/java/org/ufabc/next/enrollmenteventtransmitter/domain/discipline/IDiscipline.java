package org.ufabc.next.enrollmenteventtransmitter.domain.discipline;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cp;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;

public interface IDiscipline {
    Long id();
    String code();
    String name();
    Course course();
    Professor theoryProfessor();
    Professor practiceProfessor();
    short vacancies();
    short subscribers();
    boolean isFull();
    Shift shift();
    Cr thresholdCr();
    Cp thresholdCp();
    void changeThresholdCr(Cr cr);
    void changeThresholdCp(Cp cp);
}
