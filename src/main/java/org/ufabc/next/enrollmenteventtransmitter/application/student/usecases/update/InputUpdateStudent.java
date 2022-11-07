package org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.update;

public class InputUpdateStudent {
    public final String ra;
    public final float cp;
    public final float cr;
    public final String course;
    public final char shift;

    public InputUpdateStudent(String ra, float cp, float cr, String course, char shift){
        this.ra = ra;
        this.cp = cp;
        this.cr = cr;
        this.course = course;
        this.shift = shift;
    }
}
