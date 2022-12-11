package org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.create;

public class InputCreateStudent {
    public String name;
    public String ra;
    public String course;
    public float cr;
    public float cp;
    public char shift;

    public InputCreateStudent() {

    }

    public InputCreateStudent(String name, String ra, String course, float cr, float cp, char shift) {
        this.name = name;
        this.ra = ra;
        this.course = course;
        this.cr = cr;
        this.cp = cp;
        this.shift = shift;
    }
}