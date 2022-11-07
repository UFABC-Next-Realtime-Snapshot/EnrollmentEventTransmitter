package org.ufabc.next.enrollmenteventtransmitter.application.commands;

import org.ufabc.next.enrollmenteventtransmitter.abstraction.ICommand;

public class GetStudentByIDCommand implements ICommand {
    public String id;

    public GetStudentByIDCommand(String id) {
        this.id = id;
    }
}
