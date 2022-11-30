package org.ufabc.next.enrollmenteventtransmitter.abstraction;

public interface ICommandHandler<TCommand extends ICommand, TResponse> {
    TResponse handle(TCommand command);
}


