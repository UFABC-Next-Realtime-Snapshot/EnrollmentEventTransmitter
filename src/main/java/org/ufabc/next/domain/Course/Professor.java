package org.ufabc.next.domain.Course;

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
