package org.ufabc.next.enrollmenteventtransmitter.application;

public interface IEventHandler<T extends IEvent> {
    void handle(T event);
}
