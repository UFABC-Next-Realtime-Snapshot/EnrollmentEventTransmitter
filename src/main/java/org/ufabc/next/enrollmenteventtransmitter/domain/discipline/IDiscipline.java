package org.ufabc.next.enrollmenteventtransmitter.domain.discipline;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cp;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;

public interface IDiscipline {
    String code();
    String name();
    Course course();
    Professor theoryProfessor();
    Professor practiceProfessor();
    short vacancies();
    Shift shift();
    Cr thresholdCr();
    Cp thresholdCp();
}
