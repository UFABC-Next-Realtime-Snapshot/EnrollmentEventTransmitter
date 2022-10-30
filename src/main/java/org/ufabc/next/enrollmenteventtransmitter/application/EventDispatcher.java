package org.ufabc.next.enrollmenteventtransmitter.application;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;

@ApplicationScoped
@SuppressWarnings("unchecked")
public class EventDispatcher implements IEventDispatcher {
    private final Map<String, List<IEventHandler<IEvent>>> handlers;

    public EventDispatcher() {
        this.handlers = new HashMap<>();
    }

    @Override
    public void notify(IEvent event) {
        final String eventName = event.getClass().getName();
        final List<IEventHandler<IEvent>> handlersOfEvent = this.handlers.get(eventName);
        if (handlersOfEvent != null) {
            handlersOfEvent.forEach(h -> h.handle(event));
        }
    }

    @Override
    public <T extends IEvent> void subscribe(Class<T> event, IEventHandler<T> handler) {
        final String eventName = event.getName();
        List<IEventHandler<IEvent>> handlersOfEvent = this.handlers.get(eventName);
        if (handlersOfEvent == null) {
            handlersOfEvent = new ArrayList<>();
            handlersOfEvent.add((IEventHandler<IEvent>) handler);
            this.handlers.put(eventName, handlersOfEvent);
            return;
        }
        handlersOfEvent.add((IEventHandler<IEvent>) handler);
    }

    @Override
    public <T extends IEvent> void unsubscribe(Class<T> event, IEventHandler<T> handler) {
        final String eventName = event.getName();
        List<IEventHandler<IEvent>> handlersOfEvent = this.handlers.get(eventName);
        if(handlersOfEvent != null){
            handlersOfEvent.remove((IEventHandler<IEvent>) handler);
        }
    }
}
