package org.ufabc.next.domain.student;

import org.ufabc.next.domain.commons.exceptions.InvalidCp;
import org.ufabc.next.domain.commons.exceptions.InvalidCr;
import org.ufabc.next.domain.commons.valueObjects.Cp;
import org.ufabc.next.domain.commons.valueObjects.Cr;
import org.ufabc.next.domain.commons.valueObjects.Ra;
import org.ufabc.next.domain.commons.valueObjects.Shift;

public class Student implements IStudent{
    private final String name;
    private final Ra ra;
    private final Cr cr;
    private final Cp cp;
    private final Shift shift;

    public Student(String name, Ra ra, Cr cr, Cp cp, Shift shift){
        this.name = name;
        this.ra = ra;
        this.cr = cr;
        this.cp = cp;
        this.shift = shift;
    }

    public Student(String name, String ra, float cr, float cp, Shift shift) throws InvalidStudent{
        try{
            this.name = name;
            this.ra = new Ra(ra);
            this.cr = new Cr(cr);
            this.cp = new Cp(cp);
            this.shift = shift;
        } catch(InvalidCp | InvalidCr e){
            throw new InvalidStudent(e.getMessage());
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
