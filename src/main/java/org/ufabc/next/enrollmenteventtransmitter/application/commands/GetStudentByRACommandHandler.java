package org.ufabc.next.enrollmenteventtransmitter.application.commands;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import org.ufabc.next.enrollmenteventtransmitter.abstraction.ICommandHandler;
import org.ufabc.next.enrollmenteventtransmitter.application.viewmodels.StudentViewModel;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.IStudent;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;

@ApplicationScoped
public class GetStudentByRACommandHandler implements ICommandHandler<GetStudentByRACommand, StudentViewModel> {
    private StudentRepository _studentRepository;
    public GetStudentByRACommandHandler(
        StudentRepository studentRepository
    ) {
        _studentRepository = studentRepository;
    }

    @Override
    public StudentViewModel handle(GetStudentByRACommand command) {
        IStudent student = _studentRepository.findByRa(command.ra).get();
        return new StudentViewModel(student.ra(), student.name());
    }
}
