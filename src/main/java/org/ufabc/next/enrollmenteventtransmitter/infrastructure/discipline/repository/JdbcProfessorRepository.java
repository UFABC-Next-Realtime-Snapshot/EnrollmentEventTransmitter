package org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.repository;

import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Professor;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.ProfessorRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class JdbcProfessorRepository implements ProfessorRepository {
    @Override
    public Professor add(Professor professor) {
        var professorEntity = ProfessorEntity.toEntity(professor);
        professorEntity.persist();
        return ProfessorEntity.toModel(professorEntity);
    }

    @Override
    public Optional<Professor> findByName(String name) {
        if (name == null) {
            return Optional.empty();
        }

        var entity = ProfessorEntity
                .find("name = :name", Map.of("name", name))
                .singleResultOptional();

        if (entity.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(ProfessorEntity.toModel((ProfessorEntity) entity.get()));
    }
}
