package org.ufabc.next.enrollmenteventtransmitter.application.student.events;

import javax.enterprise.context.ApplicationScoped;

import org.ufabc.next.enrollmenteventtransmitter.application.commons.events.IEventHandler;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.websocket.DisciplineWebSocket;

@ApplicationScoped
public class StudentRegisteredInDisciplineHandler implements IEventHandler<StudentRegisteredInDiscipline>{

    private final DisciplineWebSocket webSocket;

    public StudentRegisteredInDisciplineHandler(DisciplineWebSocket webSocket){
        this.webSocket = webSocket;
    }

    @Override
    public void handle(StudentRegisteredInDiscipline event){
        webSocket.onMessage("", event.discipline().code());
    }
    
}
