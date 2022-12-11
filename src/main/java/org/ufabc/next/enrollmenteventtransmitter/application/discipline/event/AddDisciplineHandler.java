package org.ufabc.next.enrollmenteventtransmitter.application.discipline.event;

import org.ufabc.next.enrollmenteventtransmitter.application.commons.events.IEventHandler;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.websocket.DisciplineWebSocket;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AddDisciplineHandler implements IEventHandler<AddDisciplineEvent> {

    private final DisciplineWebSocket disciplineWebSocket;

    public AddDisciplineHandler(DisciplineWebSocket disciplineWebSocket) {
        this.disciplineWebSocket = disciplineWebSocket;
    }

    @Override
    public void handle(AddDisciplineEvent event) {
        disciplineWebSocket.addDisciplineInSession(event.code());
    }
}
