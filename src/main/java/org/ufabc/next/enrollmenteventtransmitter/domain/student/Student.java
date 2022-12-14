package org.ufabc.next.enrollmenteventtransmitter.domain.student;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.exceptions.InvalidCpException;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.exceptions.InvalidCrException;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.*;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;

import java.util.List;

public class Student implements IStudent {
    private final Long id;
    private final String name;
    private final Ra ra;
    private final Cr cr;
    private final Cp cp;
    private final Course course;
    private final Shift shift;
    private final List<IDiscipline> disciplines;

    public Student(Long id, String name, String ra, float cr, float cp,
                   Course course, Shift shift, List<IDiscipline> disciplines) {
        try {
            this.id = id;
            this.name = name;
            this.ra = new Ra(ra);
            this.cr = new Cr(cr);
            this.cp = new Cp(cp);
            this.course = course;
            this.shift = shift;
            this.disciplines = disciplines;
        } catch (InvalidCpException | InvalidCrException e) {
            throw new InvalidStudentException(e.getMessage());
        }
    }

    @Override
    public Long id(){
        return this.id;
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
        return this.course.name().equals(course.name());
    }

    @Override
    public Shift shift() {
        return this.shift;
    }

    @Override
    public Course course() {
        return this.course;
    }

    @Override
    public List<IDiscipline> disciplines(){
        return this.disciplines;
    }

    @Override
    public int hashCode() {
        return this.ra.value().hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object.getClass() == Student.class) {
            return this.hashCode() == object.hashCode();
        }
        return false;
    }
}
