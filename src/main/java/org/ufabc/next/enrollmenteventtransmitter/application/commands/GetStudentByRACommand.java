package org.ufabc.next.enrollmenteventtransmitter.application.commands;

import org.ufabc.next.enrollmenteventtransmitter.abstraction.ICommand;

public class GetStudentByRACommand implements ICommand {
    public String ra;

    public GetStudentByRACommand(String ra) {
        this.ra = ra;
    }
}
