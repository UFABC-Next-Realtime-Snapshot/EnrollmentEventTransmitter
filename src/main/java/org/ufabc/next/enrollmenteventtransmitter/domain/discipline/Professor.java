package org.ufabc.next.enrollmenteventtransmitter.domain.discipline;

public class Professor implements IProfessor {
    private final String name;

    public Professor(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return this.name;
    }
}
