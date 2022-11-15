package org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.enroll;

import java.util.List;

public class InputEnrollStudent {
    public final String ra;
    public final String name;
    public final String course;
    public final Float cr;
    public final Float cp;
    public final char shift;
    public final List<String> disciplineCodes;

    public InputEnrollStudent(String ra, String name, String course, Float cr, Float cp, char shift, List<String> disciplineCodes){
        this.ra = ra;
        this.name = name;
        this.course = course;
        this.cr = cr;
        this.cp = cp;
        this.shift = shift;
        this.disciplineCodes = disciplineCodes;
    }
}
