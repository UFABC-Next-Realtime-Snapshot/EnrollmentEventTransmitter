package org.ufabc.next.enrollmenteventtransmitter.application.commons.events;

public interface IEventHandler<T extends IEvent> {
    void handle(T event);
}
