package org.ufabc.next.enrollmenteventtransmitter.infrastructure.rest.request;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.*;

public class StudentRequest {
    private final String name;
    private final Ra ra;
    private final Cr cr;
    private final Cp cp;
    private final Course course;
    private final Shift shift;

    public StudentRequest(String name, Ra ra, Cr cr, Cp cp, Course course, Shift shift) {
        this.name = name;
        this.ra = ra;
        this.cr = cr;
        this.cp = cp;
        this.course = course;
        this.shift = shift;
    }
}
