package org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.DisciplineRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;

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
        Short subscribers = entityManager.createNativeQuery(
                "SELECT COUNT(student_id) FROM enrollments WHERE discipline_id =" + discipline.id() + ")",
                Short.class).unwrap(Short.class);

        DisciplineEntity.update(
                "UPDATE disciplines SET threshold_cr = :thresholdCr, threshold_cp = :thresholdCp, " +
                        "subscribers = :subscribers WHERE code = :code",
                Map.of(
                        "thresholdCr", discipline.thresholdCr(),
                        "thresholdCp", discipline.thresholdCp(),
                        "code", discipline.code(),
                        "subscribers", subscribers));
    }

    @Override
    public Optional<IDiscipline> findByCode(String code) {
        var entity = DisciplineEntity
                .find("SELECT * FROM disciplines WHERE code = :code", Map.of("code", code))
                .singleResultOptional();

        if (entity.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(DisciplineEntity.toModel((DisciplineEntity) entity.get()));
    }

    @Override
    public List<IDiscipline> findAll() {
        return DisciplineEntity.findAll().list().stream().map((e) -> DisciplineEntity.toModel((DisciplineEntity) e))
                .toList();
    }
}
