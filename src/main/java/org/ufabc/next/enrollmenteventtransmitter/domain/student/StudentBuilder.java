package org.ufabc.next.enrollmenteventtransmitter.domain.student;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;

import java.util.ArrayList;
import java.util.List;

public class StudentBuilder {
    private final Long id;
    private final String name;
    private final String ra;
    private final Shift shift;
    private float cr = 0;
    private float cp = 0;
    private Course course;
    private List<IDiscipline> disciplines = List.of();

    public StudentBuilder(Long id, String name, String ra, Shift shift) {
        this.id = id;
        this.name = name;
        this.ra = ra;
        this.shift = shift;
        this.disciplines = new ArrayList<>();
    }

    public StudentBuilder withCourse(Course course) {
        this.course = course;
        return this;
    }

    public StudentBuilder withCr(float value) {
        this.cr = value;
        return this;
    }

    public StudentBuilder withCp(float value) {
        this.cp = value;
        return this;
    }

    public StudentBuilder withDisciplines(List<IDiscipline> disciplines){
        this.disciplines = disciplines;
        return this;
    }

    public IStudent build() throws InvalidStudentException {
        return new Student(id, name, ra, cr, cp, course, shift, disciplines);
    }
}
