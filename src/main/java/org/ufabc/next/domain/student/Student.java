package org.ufabc.next.domain.Student;

import org.ufabc.next.domain.commons.exceptions.InvalidCpException;
import org.ufabc.next.domain.commons.exceptions.InvalidCrException;
import org.ufabc.next.domain.commons.valueObjects.Cp;
import org.ufabc.next.domain.commons.valueObjects.Cr;
import org.ufabc.next.domain.commons.valueObjects.Ra;
import org.ufabc.next.domain.commons.valueObjects.Shift;

public class Student implements IStudent{
    private final String name;
    private final Ra ra;
    private final Cr cr;
    private final Cp cp;
    private final boolean reservation;
    private final Shift shift;

    public Student(String name, Ra ra, Cr cr, Cp cp, boolean reservation, Shift shift){
        this.name = name;
        this.ra = ra;
        // TODO what if CR/CP is null?
        this.cr = cr;
        this.cp = cp;
        this.reservation = reservation;
        this.shift = shift;
    }

    public Student(String name, String ra, float cr, float cp, boolean reservation, Shift shift) throws InvalidStudentException{
        try{
            this.name = name;
            this.ra = new Ra(ra);
            this.cr = new Cr(cr);
            this.cp = new Cp(cp);
            this.reservation = reservation;
            this.shift = shift;
        } catch(InvalidCpException | InvalidCrException e){
            throw new InvalidStudentException(e.getMessage());
        }
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Ra ra() {
        return this.ra;
    }

    @Override
    public Cr cr() {
        return this.cr;
    }

    @Override
    public Cp cp() {
        return this.cp;
    }

    @Override
    public boolean reservation() {
        return this.reservation;
    }

    @Override
    public Shift shift() {
        return this.shift;
    }

    @Override
    public int hashCode(){
        return this.ra.value().hashCode();
    }

    @Override
    public boolean equals(Object object){
        if(object.getClass() == Student.class){
            return this.hashCode() == ((Student) object).hashCode();
        }
        return false;
    }
}
