package org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.repository;

import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Professor;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.ProfessorRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class JdbcProfessorRepository implements ProfessorRepository {
    @Override
    public void add(Professor professor) {
        ProfessorEntity.toEntity(professor).persist();
    }

    @Override
    public Optional<Professor> findByName(String name) {
        var entity = ProfessorEntity
                .find("name = :name", Map.of("name", name))
                .singleResultOptional();

        if (entity.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(ProfessorEntity.toModel((ProfessorEntity) entity.get()));
    }
}
