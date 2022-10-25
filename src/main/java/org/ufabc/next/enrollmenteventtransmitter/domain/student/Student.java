package org.ufabc.next.enrollmenteventtransmitter.domain.student;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.exceptions.InvalidCpException;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.exceptions.InvalidCrException;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cp;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Ra;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;

public class Student implements IStudent{
    private final String name;
    private final Ra ra;
    private final Cr cr;
    private final Cp cp;
    private final Course course;
    private final Shift shift;

    public Student(String name, String ra, float cr, float cp, Course course, Shift shift) throws InvalidStudentException{
        try{
            this.name = name;
            this.ra = new Ra(ra);
            this.cr = new Cr(cr);
            this.cp = new Cp(cp);
            this.course = course;
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
    public boolean reservation(Course course) {
        return this.course.equals(course);
    }

    @Override
    public Shift shift() {
        return this.shift;
    }

    @Override
    public Course course(){
        return this.course;
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
