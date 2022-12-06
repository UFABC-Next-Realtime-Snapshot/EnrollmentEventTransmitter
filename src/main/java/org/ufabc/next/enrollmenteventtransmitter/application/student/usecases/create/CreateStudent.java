package org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.create;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.exceptions.ResourceNotFoundException;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.CourseRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentBuilder;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;

@ApplicationScoped
public class CreateStudent {
    private static final Logger LOGGER = Logger.getLogger(CreateStudent.class);

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public CreateStudent(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public OutputCreateStudent execute(InputCreateStudent input) {
        var optionalStudent = studentRepository.findByRa(input.ra);
        var optionalCourse = courseRepository.findByName(input.course);

        if (optionalStudent.isPresent()) {
            throw new ResourceNotFoundException("O estudante ja existe");
        }

        if (optionalCourse.isEmpty()) {
            throw new ResourceNotFoundException("Nao achei o curso");
        }

        var course = optionalCourse.get();
        var student = new StudentBuilder(null, input.name, input.ra, Shift.fromInitial(input.shift))
                .withCr(input.cr)
                .withCp(input.cp)
                .withCourse(course)
                .build();
        studentRepository.add(student);
        LOGGER.info("student created");
        return new OutputCreateStudent();
    }
}
