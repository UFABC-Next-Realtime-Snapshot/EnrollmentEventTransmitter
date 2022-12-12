package org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.enroll;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;
import org.ufabc.next.enrollmenteventtransmitter.application.student.events.StudentEventDispatcher;
import org.ufabc.next.enrollmenteventtransmitter.application.student.events.StudentRegisteredInDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.application.student.events.StudentRemovedFromDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.application.student.services.CalculateCoefficientsOfDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.exceptions.ResourceNotFoundException;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.CourseRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.DisciplineRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.IStudent;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentBuilder;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;

@ApplicationScoped
public class EnrollStudent {

    private final DisciplineRepository disciplineRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CalculateCoefficientsOfDiscipline calculateCoefficientsOfDiscipline;
    private final StudentEventDispatcher dispatcher;
    private static final Logger LOGGER = Logger.getLogger(EnrollStudent.class);

    public EnrollStudent(
            DisciplineRepository disciplineRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            CalculateCoefficientsOfDiscipline calculateCoefficientsOfDiscipline,
            StudentEventDispatcher dispatcher) {
        this.disciplineRepository = disciplineRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.calculateCoefficientsOfDiscipline = calculateCoefficientsOfDiscipline;
        this.dispatcher = dispatcher;
    }

    private List<IDiscipline> findAllDisciplinesRequestedByStudent(List<String> codes) {
        var disciplines = new ArrayList<IDiscipline>();
        for (String code : codes) {
            var optionalDiscipline = disciplineRepository.findByCode(code);
            if (optionalDiscipline.isEmpty()) {
                throw new ResourceNotFoundException(String.format("discipline code %s not exists", code));
            }
            disciplines.add(optionalDiscipline.get());
        }
        return disciplines;
    }

    private Course findCourseRequestedByStudent(String name) {
        var optionalCourse = courseRepository.findByName(name);
        if (optionalCourse.isEmpty()) {
            throw new ResourceNotFoundException(String.format("course %s not exists", name));
        }
        return optionalCourse.get();
    }

    private List<IDiscipline> disciplinesToGetOut(IStudent student, List<IDiscipline> disciplinesForEnrollment) {
        List<String> disciplinesCodesForEnrollment = disciplinesForEnrollment
                .stream()
                .map(discipline -> discipline.code().toLowerCase())
                .toList();
        return student.disciplines()
                .stream()
                .filter(discipline -> !disciplinesCodesForEnrollment.contains(discipline.code().toLowerCase()))
                .toList();
    }

    @Transactional
    public OutputEnrollStudent execute(InputEnrollStudent input) {
        var disciplines = findAllDisciplinesRequestedByStudent(input.disciplineCodes);
        var course = findCourseRequestedByStudent(input.course);

        var optionalStudent = studentRepository.findByRa(input.ra);
        if (optionalStudent.isEmpty()) {
            var student = new StudentBuilder(
                    null, input.name, input.ra, Shift.fromInitial(input.shift))
                    .withCourse(course)
                    .withCr(input.cr)
                    .withCp(input.cp)
                    .withDisciplines(disciplines)
                    .build();
            studentRepository.add(student);
            LOGGER.info("created student " + input.ra);
            for (IDiscipline discipline : student.disciplines()) {
                calculateCoefficientsOfDiscipline.execute(discipline);
                disciplineRepository.update(discipline);
                dispatcher.notify(new StudentRegisteredInDiscipline(discipline, student));
            }
            return new OutputEnrollStudent();
        }

        var student = optionalStudent.get();
        var disciplinesToGetOut = disciplinesToGetOut(student, disciplines);

        var studentWithNewDisciplines = new StudentBuilder(
                student.id(),
                student.name(),
                student.ra().value(),
                Shift.fromInitial(input.shift))
                .withCp(input.cp)
                .withCr(input.cr)
                .withCourse(course)
                .withDisciplines(disciplines)
                .build();

        studentRepository.update(studentWithNewDisciplines);
        for (IDiscipline discipline : disciplinesToGetOut) {
            calculateCoefficientsOfDiscipline.execute(discipline);
            disciplineRepository.update(discipline);
            dispatcher.notify(new StudentRemovedFromDiscipline(discipline, studentWithNewDisciplines));
        }

        for (IDiscipline discipline : studentWithNewDisciplines.disciplines()) {
            calculateCoefficientsOfDiscipline.execute(discipline);
            disciplineRepository.update(discipline);
            dispatcher.notify(new StudentRegisteredInDiscipline(discipline, studentWithNewDisciplines));
        }
        return new OutputEnrollStudent();
    }
}
