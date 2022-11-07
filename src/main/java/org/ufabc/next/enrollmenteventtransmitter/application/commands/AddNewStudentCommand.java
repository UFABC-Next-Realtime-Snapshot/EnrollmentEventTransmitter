package org.ufabc.next.enrollmenteventtransmitter.application.commands;

import org.ufabc.next.enrollmenteventtransmitter.abstraction.ICommand;

public class AddNewStudentCommand implements ICommand {
    public String name;

    public AddNewStudentCommand(String name) {
        this.name = name;
    }
}
