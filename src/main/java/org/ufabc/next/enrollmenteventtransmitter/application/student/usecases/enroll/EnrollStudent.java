package org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.enroll;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;

import org.jboss.logging.Logger;
import org.ufabc.next.enrollmenteventtransmitter.application.commons.events.IEventDispatcher;
import org.ufabc.next.enrollmenteventtransmitter.application.student.events.StudentRegisteredInDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.application.student.events.StudentRemovedFromDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.application.student.services.CalculateCoefficientsOfDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.create.CreateStudent;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.update.UpdateStudent;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.CourseRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Discipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.DisciplineRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.IStudent;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentBuilder;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.commons.rest.CustomExceptionHandler;

@RequestScoped
public class EnrollStudent {

    private final DisciplineRepository disciplineRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CalculateCoefficientsOfDiscipline calculateCoefficientsOfDiscipline;
    private final IEventDispatcher dispatcher;
    private static final Logger LOGGER = Logger.getLogger(EnrollStudent.class);

    public EnrollStudent(
            DisciplineRepository disciplineRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            CalculateCoefficientsOfDiscipline calculateCoefficientsOfDiscipline,
            IEventDispatcher dispatcher) {
        this.disciplineRepository = disciplineRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.calculateCoefficientsOfDiscipline = calculateCoefficientsOfDiscipline;
        this.dispatcher = dispatcher;
    }

    private List<IDiscipline> findAllDisciplinesRequestedByStudent(List<String> codes) throws RuntimeException{
        var disciplines = new ArrayList<IDiscipline>();
        for(String code : codes){
            var optionalDiscipline = disciplineRepository.findByCode(code);
            if(optionalDiscipline.isEmpty()) throw new RuntimeException("discipline code: " + code + " not exists");
            disciplines.add(optionalDiscipline.get());
        }
        return disciplines;
    }

    private Course findCourseRequestedByStudent(String name) throws RuntimeException{
        var optionalCourse = courseRepository.findByName(name);
        if (optionalCourse.isEmpty()) throw new RuntimeException("course: " + name + " not exists");
        return optionalCourse.get();
    }

    private List<IDiscipline> disciplinesToGetOut(IStudent student, List<IDiscipline> disciplinesForEnrollment){
        return student.disciplines().stream().filter(discipline -> !disciplinesForEnrollment.contains(discipline)).toList();
    }

    public OutputEnrollStudent execute(InputEnrollStudent input){
        var disciplines = findAllDisciplinesRequestedByStudent(input.disciplineCodes);
        var course = findCourseRequestedByStudent(input.course);

        var optionalStudent = studentRepository.findByRa(input.ra);
        if(optionalStudent.isEmpty()){
            studentRepository.add(new StudentBuilder(null, input.name, input.ra, Shift.fromInitial(input.shift))
                    .withCourse(course)
                    .withCr(input.cr)
                    .withCp(input.cp)
                    .withDisciplines(disciplines)
                    .build()
            );
            LOGGER.info("created student " + input.ra);
            return new OutputEnrollStudent();
        }

        var student = optionalStudent.get();
        var disciplinesToGetOut = disciplinesToGetOut(student, disciplines);

        var studentWithNewDisciplines = new StudentBuilder(
                student.id(),
                student.name(),
                student.ra().toString(),
                student.shift())
                    .withCp(student.cp().value())
                    .withCr(student.cr().value())
                    .withCourse(student.course())
                    .withDisciplines(disciplines).build();

        studentRepository.update(student);
        for(IDiscipline discipline : disciplinesToGetOut){
            calculateCoefficientsOfDiscipline.execute(discipline);
            disciplineRepository.update(discipline);
            dispatcher.notify(new StudentRemovedFromDiscipline(discipline, studentWithNewDisciplines));
        }

        for(IDiscipline discipline : student.disciplines()){
            calculateCoefficientsOfDiscipline.execute(discipline);
            disciplineRepository.update(discipline);
            dispatcher.notify(new StudentRegisteredInDiscipline(discipline, studentWithNewDisciplines));
        }
        LOGGER.info(
                "enrolled student " +
                        student.ra() + " in [" +
                        disciplines.stream().map(IDiscipline::code).collect(Collectors.joining(",")) + "]");
        return new OutputEnrollStudent();
    }
}