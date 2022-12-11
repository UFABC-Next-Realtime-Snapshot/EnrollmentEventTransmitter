package org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.repository;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Professor;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.ProfessorRepository;
import org.ufabc.next.enrollmenteventtransmitter.util.Cleanable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class JdbcProfessorRepositoryTest extends Cleanable {
    @Inject
    ProfessorRepository professorRepository;

    @Test
    @Transactional
    public void shouldSave() {
        String name = "aName";
        Professor professor = new Professor(name);

        var savedProfessor = professorRepository.add(professor);

        assertEquals(1, savedProfessor.id());
        assertEquals(professor.name(), savedProfessor.name());
    }

    @Test
    public void shouldNotFindProfessor() {
        var professor = professorRepository.findByName("aProfessor");

        assertFalse(professor.isPresent());
    }

    @Test
    @Transactional
    public void shouldFindProfessor() {
        String name = "aName";
        Professor professor = new Professor(name);
        professorRepository.add(professor);

        var persistedProfessor = professorRepository.findByName(name);

        assertTrue(persistedProfessor.isPresent());

        assertEquals(1, persistedProfessor.get().id());
        assertEquals(professor.name(), persistedProfessor.get().name());
    }
}
