package org.ufabc.next.enrollmenteventtransmitter.domain.student;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Course;

public class StudentBuilder {

    private String name;
    private String ra;
    private Shift shift;
    private float cr = 0;
    private float cp = 0;
    private Course course;

    public StudentBuilder(String name, String ra, Shift shift){
        this.name = name;
        this.ra = ra;
        this.shift = shift;
    }

    public StudentBuilder WithCourse(Course course){
        this.course = course;
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
        return new Student(name, ra, cr, cp, course, shift);
    }
}
