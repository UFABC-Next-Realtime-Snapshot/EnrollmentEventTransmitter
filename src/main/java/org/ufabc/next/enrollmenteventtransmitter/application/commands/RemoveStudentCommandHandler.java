package org.ufabc.next.enrollmenteventtransmitter.application.commands;

import javax.enterprise.context.ApplicationScoped;

import org.ufabc.next.enrollmenteventtransmitter.abstraction.ICommandHandler;

@ApplicationScoped
public class RemoveStudentCommandHandler implements ICommandHandler<RemoveStudentCommand> {
    public RemoveStudentCommandHandler() { }

    @Override
    public void handle(RemoveStudentCommand command) {
        // TODO Auto-generated method stub
    }
}
