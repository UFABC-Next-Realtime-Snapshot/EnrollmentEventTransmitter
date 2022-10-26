package org.ufabc.next.enrollmenteventtransmitter.domain.discipline;

import java.util.Objects;

public class Professor implements IProfessor {
    private final String name;

    public Professor(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Professor professor = (Professor) other;
        return Objects.equals(name, professor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
