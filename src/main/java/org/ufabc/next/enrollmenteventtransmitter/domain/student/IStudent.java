package org.ufabc.next.enrollmenteventtransmitter.domain.student;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cp;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Ra;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;

public interface IStudent {
    String name();
    Ra ra();
    Cr cr();
    Cp cp();
    boolean reservation(Course course);
    Shift shift();
    Course course();
}