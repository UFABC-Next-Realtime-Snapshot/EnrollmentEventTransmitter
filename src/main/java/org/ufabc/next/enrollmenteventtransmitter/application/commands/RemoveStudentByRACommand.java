package org.ufabc.next.enrollmenteventtransmitter.application.commands;

import org.ufabc.next.enrollmenteventtransmitter.abstraction.ICommand;

public class RemoveStudentByRACommand implements ICommand {
    public String ra;

    public RemoveStudentByRACommand(String ra) {
        this.ra = ra;
    }
}
