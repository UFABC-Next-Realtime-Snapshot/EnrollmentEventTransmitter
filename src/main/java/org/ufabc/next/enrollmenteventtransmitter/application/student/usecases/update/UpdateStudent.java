package org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.update;

import javax.enterprise.context.ApplicationScoped;

import org.ufabc.next.enrollmenteventtransmitter.application.student.services.CalculateCoefficientsOfDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.CourseRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.DisciplineRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentBuilder;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;

@ApplicationScoped
public class UpdateStudent {

    private final StudentRepository studentRepository;
    private final DisciplineRepository disciplineRepository;
    private final CourseRepository courseRepository;
    private final CalculateCoefficientsOfDiscipline calculateCoefficientsOfDiscipline;

    public UpdateStudent(StudentRepository studentRepository,
            DisciplineRepository disciplineRepository,
            CourseRepository courseRepository,
            CalculateCoefficientsOfDiscipline calculateCoefficientsOfDiscipline) {
        this.studentRepository = studentRepository;
        this.disciplineRepository = disciplineRepository;
        this.courseRepository = courseRepository;
        this.calculateCoefficientsOfDiscipline = calculateCoefficientsOfDiscipline;
    }

    public OutputUpdateStudent execute(InputUpdateStudent input) {
        var optionalStudent = studentRepository.findByRa(input.ra);
        if (optionalStudent.isEmpty()) {
            throw new RuntimeException("student " + input.ra + " not exists");
        }

        var optionalCourse = courseRepository.findByName(input.course);
        if (optionalCourse.isEmpty()) {
            throw new RuntimeException("course " + input.course + " not exists");
        }

        var course = optionalCourse.get();
        var student = optionalStudent.get();
        var shift = Shift.fromInitial(input.shift);
        student = new StudentBuilder(student.id(), student.name(), student.ra().toString(), shift)
                .withCp(input.cp)
                .withCr(input.cr)
                .withDisciplines(student.disciplines())
                .withCourse(course)
                .build();
        studentRepository.update(student);

        student.disciplines().forEach(discipline -> {
            calculateCoefficientsOfDiscipline.execute(discipline);
            disciplineRepository.update(discipline);
        });
        return new OutputUpdateStudent();
    }
}
