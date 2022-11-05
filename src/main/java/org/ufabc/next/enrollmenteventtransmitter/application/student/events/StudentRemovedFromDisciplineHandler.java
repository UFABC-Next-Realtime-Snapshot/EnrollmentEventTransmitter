package org.ufabc.next.enrollmenteventtransmitter.application.student.events;

import javax.inject.Inject;

import org.ufabc.next.enrollmenteventtransmitter.application.commons.events.IEventHandler;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.websocket.DisciplineWebSocket;

public class StudentRemovedFromDisciplineHandler implements IEventHandler<StudentRemovedFromDiscipline>{
    @Inject
    DisciplineWebSocket disciplineWebSocket;

    @Override
    public void handle(StudentRemovedFromDiscipline event) {
        // Fazer um log ou algo do tipo, alem de avisar a conexao da disciplina
        disciplineWebSocket.onMessage("", event.discipline().code());
    }
}
