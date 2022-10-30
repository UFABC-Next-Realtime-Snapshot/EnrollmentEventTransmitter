package org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Professor;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.ProfessorRepository;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc.framework.JdbcTemplate;

import javax.inject.Inject;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class JdbcProfessorRepositoryTest {
    @Inject
    ProfessorRepository professorRepository;

    @Inject
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void clean() {
        jdbcTemplate.update("DELETE FROM professors", statement -> {
        });
    }

    @Test
    public void shouldAddAndReturn() {
        Professor professor = new Professor("Thomas");
        professorRepository.add(professor);

        Optional<Professor> existentProfessor = findByName(professor.name());

        assertTrue(existentProfessor.isPresent());
        assertEquals(professor, existentProfessor.get());
    }

    @Test
    public void shouldNotReturnWhenProfessorNoExist() {
        Professor professor = new Professor("Carlos");

        Optional<Professor> existentProfessor = findByName(professor.name());

        assertTrue(existentProfessor.isEmpty());
    }

    public Optional<Professor> findByName(String name) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM professors WHERE name = ?",
                (statement) -> statement.setString(1, name),
                new JdbcProfessorRepository.ProfessorRowMapper()));
    }
}
