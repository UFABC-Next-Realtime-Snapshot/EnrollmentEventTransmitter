package org.ufabc.next.enrollmenteventtransmitter.application.student.events;


import org.ufabc.next.enrollmenteventtransmitter.application.commons.events.IEventHandler;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.websocket.DisciplineWebSocket;

public class StudentRemovedFromDisciplineHandler implements IEventHandler<StudentRemovedFromDiscipline>{

    private final DisciplineWebSocket webSocket;

    public StudentRemovedFromDisciplineHandler(DisciplineWebSocket webSocket){
        this.webSocket = webSocket;
    }

    @Override
    public void handle(StudentRemovedFromDiscipline event) {
        webSocket.onMessage("", event.discipline().code());
    }
}
