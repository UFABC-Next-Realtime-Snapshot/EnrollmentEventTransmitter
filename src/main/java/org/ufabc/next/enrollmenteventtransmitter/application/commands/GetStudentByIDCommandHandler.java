package org.ufabc.next.enrollmenteventtransmitter.application.commands;

import javax.enterprise.context.ApplicationScoped;

import org.ufabc.next.enrollmenteventtransmitter.abstraction.ICommandHandler;

@ApplicationScoped
public class GetStudentByIDCommandHandler implements ICommandHandler<GetStudentByIDCommand> {
    public GetStudentByIDCommandHandler() { }

    @Override
    public void handle(GetStudentByIDCommand command) {
        // TODO Auto-generated method stub
    }
}
