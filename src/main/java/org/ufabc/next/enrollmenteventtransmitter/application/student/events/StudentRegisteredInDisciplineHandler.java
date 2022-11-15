package org.ufabc.next.enrollmenteventtransmitter.application.student.events;

import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.ufabc.next.enrollmenteventtransmitter.application.commons.events.IEventHandler;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.commons.rest.CustomExceptionHandler;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.websocket.DisciplineWebSocket;

public class StudentRegisteredInDisciplineHandler implements IEventHandler<StudentRegisteredInDiscipline>{

    @Inject
    DisciplineWebSocket disciplineWebSocket;

    private static final Logger LOGGER = Logger.getLogger(StudentRegisteredInDisciplineHandler.class);

    @Override
    public void handle(StudentRegisteredInDiscipline event){
        disciplineWebSocket.onMessage("", event.discipline().code());
    }
    
}
