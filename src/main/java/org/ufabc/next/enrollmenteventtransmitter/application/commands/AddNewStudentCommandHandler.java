package org.ufabc.next.enrollmenteventtransmitter.application.commands;

import javax.enterprise.context.ApplicationScoped;

import org.ufabc.next.enrollmenteventtransmitter.abstraction.ICommandHandler;

@ApplicationScoped
public class AddNewStudentCommandHandler implements ICommandHandler<AddNewStudentCommand> {
    public AddNewStudentCommandHandler() { }

    public void handle(AddNewStudentCommand command) {
        // TODO Auto-generated method stub
        // Cria studante
        // Salva no banco
    }
}
