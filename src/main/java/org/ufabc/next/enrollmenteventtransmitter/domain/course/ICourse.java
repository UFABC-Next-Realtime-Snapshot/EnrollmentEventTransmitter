package org.ufabc.next.enrollmenteventtransmitter.domain.course;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cp;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;

public interface ICourse {
    String code();
    String name();
    Professor theoryProfessor();
    Professor practiceProfessor();
    short vacancies();
    Shift shift();
    Cr thresholdCr();
    Cp thresholdCp();
}
