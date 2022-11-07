package org.ufabc.next.enrollmenteventtransmitter.abstraction;

public interface ICommandHandler<T extends ICommand> {
    void handle(T command);
}
