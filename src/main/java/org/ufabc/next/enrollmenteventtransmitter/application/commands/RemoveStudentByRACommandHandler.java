package org.ufabc.next.enrollmenteventtransmitter.application.commands;

import javax.enterprise.context.ApplicationScoped;

import org.ufabc.next.enrollmenteventtransmitter.abstraction.ICommandHandler;

@ApplicationScoped
public class RemoveStudentByRACommandHandler implements ICommandHandler<RemoveStudentByRACommand, Void> {
    public RemoveStudentByRACommandHandler() { }

    @Override
    public Void handle(RemoveStudentByRACommand command) {
        // TODO Auto-generated method stub
        return null;
    }
}
