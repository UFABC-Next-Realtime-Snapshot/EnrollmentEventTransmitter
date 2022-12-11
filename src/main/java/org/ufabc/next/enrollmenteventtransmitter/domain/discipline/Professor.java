package org.ufabc.next.enrollmenteventtransmitter.domain.discipline;

import java.util.Objects;

public class Professor implements IProfessor {
    private final Long id;
    private final String name;

    public Professor(String name) {
        this.id = null;
        this.name = name;
    }

    public Professor(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Long id() {
        return this.id;
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
        return Objects.equals(id, professor.id) && Objects.equals(name, professor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
