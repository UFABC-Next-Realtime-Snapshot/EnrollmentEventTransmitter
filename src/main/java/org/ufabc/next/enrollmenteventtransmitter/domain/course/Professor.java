package org.ufabc.next.enrollmenteventtransmitter.domain.course;

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
