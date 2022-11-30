package org.ufabc.next.enrollmenteventtransmitter.application.commands;

import javax.enterprise.context.ApplicationScoped;

import org.ufabc.next.enrollmenteventtransmitter.abstraction.ICommandHandler;

@ApplicationScoped
public class AddNewStudentCommandHandler implements ICommandHandler<AddNewStudentCommand, Void> {
    public AddNewStudentCommandHandler() { }

    public Void handle(AddNewStudentCommand command) {
        // TODO Auto-generated method stub
        // Cria studante
        // Salva no banco
        return null;
    }
}
