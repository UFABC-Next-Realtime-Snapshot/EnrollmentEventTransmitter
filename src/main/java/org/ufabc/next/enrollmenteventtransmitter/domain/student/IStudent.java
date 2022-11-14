package org.ufabc.next.enrollmenteventtransmitter.domain.student;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cp;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Ra;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;

import java.util.List;

public interface IStudent {
    Long id();
    String name();
    Ra ra();
    Cr cr();
    Cp cp();
    boolean reservation(Course course);
    Shift shift();
    Course course();
    List<IDiscipline> disciplines();
}
