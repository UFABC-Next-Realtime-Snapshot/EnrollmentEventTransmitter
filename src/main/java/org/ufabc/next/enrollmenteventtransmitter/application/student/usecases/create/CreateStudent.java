package org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.create;

import javax.enterprise.context.RequestScoped;

import org.jboss.logging.Logger;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentBuilder;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.commons.rest.CustomExceptionHandler;

@RequestScoped
public class CreateStudent {
    private final StudentRepository studentRepository;

    private static final Logger LOGGER = Logger.getLogger(CustomExceptionHandler.class);

    public CreateStudent(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public OutputCreateStudent execute(InputCreateStudent input){
        var optionalStudent = studentRepository.findByRa(input.ra);

        if(optionalStudent.isPresent()){
            throw new RuntimeException("Deu ruim");
        }

        var student = new StudentBuilder(null, input.name, input.ra, Shift.fromInitial(input.shift))
                .withCr(input.cr)
                .withCp(input.cp)
                .withCourse(null)
                .build();
        studentRepository.add(student);
        LOGGER.info("student created");
        return new OutputCreateStudent();
    }
}
