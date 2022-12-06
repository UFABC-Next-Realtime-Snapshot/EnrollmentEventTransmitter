package org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.repository;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.exceptions.ResourceNotFoundException;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.DisciplineRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class JdbcDisciplineRepository implements DisciplineRepository {
    @Inject
    EntityManager entityManager;

    @Override
    public void add(IDiscipline discipline) {
        DisciplineEntity.persist(DisciplineEntity.toEntity(discipline));
    }

    @Override
    public void update(IDiscipline discipline) {
        if (discipline.id() == null) {
            throw new ResourceNotFoundException(String.format("Discipline %s is nonexistent", discipline.code()));
        }

        var singleResultOptional = DisciplineEntity
                .find("id = :id", Map.of("id", discipline.id()))
                .singleResultOptional();

        if (singleResultOptional.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Discipline %s is nonexistent", discipline.code()));
        }

        var disciplineToUpdate = (DisciplineEntity) singleResultOptional.get();

        Query statement = entityManager.createNativeQuery(
                        "SELECT COUNT(student_id) FROM enrollments WHERE discipline_id = :disciplineId")
                .setParameter("disciplineId", discipline.id());

        short subscribers = ((BigInteger) statement.getResultList().get(0)).shortValue();
        
        disciplineToUpdate.setCp(discipline.thresholdCp().value());
        disciplineToUpdate.setCr(discipline.thresholdCr().value());
        disciplineToUpdate.setSubscribers(subscribers);
        disciplineToUpdate.persist();
    }

    @Override
    public Optional<IDiscipline> findByCode(String code) {
        var entity = DisciplineEntity
                .find("code = :code", Map.of("code", code))
                .singleResultOptional();

        if (entity.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(DisciplineEntity.toModel((DisciplineEntity) entity.get()));
    }

    @Override
    public List<IDiscipline> findAll() {
        return DisciplineEntity.findAll()
                .list()
                .stream()
                .map((d) -> DisciplineEntity.toModel((DisciplineEntity) d))
                .toList();
    }
}
