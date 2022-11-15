package org.ufabc.next.enrollmenteventtransmitter.domain.discipline;

import java.util.Optional;

public interface ProfessorRepository {
    void add(Professor professor);
    Optional<Professor> findByName(String name);
}
