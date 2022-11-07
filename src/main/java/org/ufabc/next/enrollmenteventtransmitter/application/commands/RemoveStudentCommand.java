package org.ufabc.next.enrollmenteventtransmitter.application.commands;

import org.ufabc.next.enrollmenteventtransmitter.abstraction.ICommand;

public class RemoveStudentCommand implements ICommand {
    public String id;

    public RemoveStudentCommand(String id) {
        this.id = id;
    }
}
