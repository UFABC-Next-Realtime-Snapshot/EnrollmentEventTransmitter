package org.ufabc.next.enrollmenteventtransmitter.application.student.events;

import org.ufabc.next.enrollmenteventtransmitter.application.commons.events.EventDispatcher;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.websocket.DisciplineWebSocket;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StudentEventDispatcher extends EventDispatcher {

    public StudentEventDispatcher(DisciplineWebSocket websocket) {
        this.subscribe(StudentRemovedFromDiscipline.class, new StudentRemovedFromDisciplineHandler(websocket));
        this.subscribe(StudentRegisteredInDiscipline.class, new StudentRegisteredInDisciplineHandler(websocket));
    }
}
