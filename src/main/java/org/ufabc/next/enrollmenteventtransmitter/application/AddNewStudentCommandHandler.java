package org.ufabc.next.enrollmenteventtransmitter.application;

import org.ufabc.next.enrollmenteventtransmitter.abstraction.ICommandHandler;

public class AddNewStudentCommandHandler implements ICommandHandler<AddNewStudentCommand> {
    public AddNewStudentCommandHandler() { }

    public void handle(AddNewStudentCommand command) {
        // Cria studante
        // Salva no banco
    }
}
