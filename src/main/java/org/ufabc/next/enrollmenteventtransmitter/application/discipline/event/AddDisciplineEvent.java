package org.ufabc.next.enrollmenteventtransmitter.application.discipline.event;

import org.ufabc.next.enrollmenteventtransmitter.application.commons.events.IEvent;

import java.util.Objects;

public class AddDisciplineEvent implements IEvent {
    private final String code;

    public AddDisciplineEvent(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddDisciplineEvent that = (AddDisciplineEvent) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
