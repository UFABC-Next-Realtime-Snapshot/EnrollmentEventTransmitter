package org.ufabc.next.enrollmenteventtransmitter.application.student.events;

import org.ufabc.next.enrollmenteventtransmitter.application.commons.events.IEvent;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.IStudent;

public class StudentRemovedFromDiscipline implements IEvent {
    private final IDiscipline discipline;
    private final IStudent student;

    public StudentRemovedFromDiscipline(final IDiscipline discipline, final IStudent student){
        this.discipline = discipline;
        this.student = student;
    }

    public IDiscipline discipline(){
        return this.discipline;
    }

    public IStudent student(){
        return this.student;
    }
}