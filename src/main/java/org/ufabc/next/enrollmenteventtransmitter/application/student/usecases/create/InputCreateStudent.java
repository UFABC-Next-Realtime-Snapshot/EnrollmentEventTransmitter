package org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.create;

public class InputCreateStudent {
    public final String name;
    public final String ra;
    public final float cr;
    public final float cp;
    public final char shift;

    public InputCreateStudent(String name, String ra, float cr, float cp, char shift){
        this.name = name;
        this.ra = ra;
        this.cr = cr;
        this.cp = cp;
        this.shift = shift;
    }
}