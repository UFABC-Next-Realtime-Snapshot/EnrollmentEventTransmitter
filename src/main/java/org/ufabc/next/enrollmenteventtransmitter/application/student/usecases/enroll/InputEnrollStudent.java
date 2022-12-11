package org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.enroll;

import java.util.List;

public class InputEnrollStudent {
    public String ra;
    public String name;
    public String course;
    public Float cr;
    public Float cp;
    public char shift;
    public List<String> disciplineCodes;

    public InputEnrollStudent(String ra, String name, String course, Float cr, Float cp, char shift, List<String> disciplineCodes){
        this.ra = ra;
        this.name = name;
        this.course = course;
        this.cr = cr;
        this.cp = cp;
        this.shift = shift;
        this.disciplineCodes = disciplineCodes;
    }

    public InputEnrollStudent() {

    }
}
