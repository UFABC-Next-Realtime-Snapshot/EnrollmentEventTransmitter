package org.ufabc.next.enrollmenteventtransmitter.application.discipline.event;

import org.ufabc.next.enrollmenteventtransmitter.application.commons.events.EventDispatcher;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.websocket.DisciplineWebSocket;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DisciplineEventDispatcher extends EventDispatcher {

    public DisciplineEventDispatcher(DisciplineWebSocket websocket) {
        this.subscribe(AddDisciplineEvent.class, new AddDisciplineHandler(websocket));
    }
}
