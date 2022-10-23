package org.ufabc.next.enrollmenteventtransmitter.domain.student;

import java.util.Optional;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;

public class StudentBuilder {

    private String name;
    private String ra;
    private Shift shift;
    private float cr = 0;
    private float cp = 0;
    private Boolean reservation;

    public StudentBuilder(String name, String ra, Shift shift){
        this.name = name;
        this.ra = ra;
        this.shift = shift;
    }

    public StudentBuilder WithReservation(){
        this.reservation = true;
        return this;
    }

    public StudentBuilder WithCr(float value){
        this.cr = value;
        return this;
    }

    public StudentBuilder WithCp(float value){
        this.cp = value;
        return this;
    }

    public IStudent build() throws InvalidStudentException{
        return new Student(name, ra, cr, cp, Optional.ofNullable(reservation).orElse(false), shift);
    }
}
