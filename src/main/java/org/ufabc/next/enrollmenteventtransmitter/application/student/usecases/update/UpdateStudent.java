package org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.update;

import javax.enterprise.context.RequestScoped;

import org.ufabc.next.enrollmenteventtransmitter.application.student.services.CalculateCoefficientsOfDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.DisciplineRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentBuilder;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.commons.repository.CourseEntity;

@RequestScoped
public class UpdateStudent {

    private final StudentRepository studentRepository;
    private final DisciplineRepository disciplineRepository;
    private final CalculateCoefficientsOfDiscipline calculateCoefficientsOfDiscipline;

    public UpdateStudent(StudentRepository studentRepository,
            DisciplineRepository disciplineRepository,
            CalculateCoefficientsOfDiscipline calculateCoefficientsOfDiscipline) {
        this.studentRepository = studentRepository;
        this.disciplineRepository = disciplineRepository;
        this.calculateCoefficientsOfDiscipline = calculateCoefficientsOfDiscipline;
    }

    public OutputUpdateStudent execute(InputUpdateStudent input) {
        var optionalStudent = studentRepository.findByRa(input.ra);
        if (optionalStudent.isEmpty()) {
            throw new RuntimeException("Deu ruim");
        }

        var optionalCourse = CourseEntity.find("name like ?1", input.course.strip()).firstResultOptional();
        if (optionalCourse.isEmpty()) {
            throw new RuntimeException("Deu ruim");
        }

        var course = CourseEntity.toModel((CourseEntity) optionalCourse.get());
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
