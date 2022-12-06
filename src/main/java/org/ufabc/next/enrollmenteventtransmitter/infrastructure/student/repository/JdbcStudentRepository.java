package org.ufabc.next.enrollmenteventtransmitter.infrastructure.student.repository;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.exceptions.ResourceNotFoundException;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.IStudent;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class JdbcStudentRepository implements StudentRepository {

    @Inject
    EntityManager entityManager;

    @Override
    public void add(IStudent student) {
        StudentEntity.toEntity(student).persist();
    }

    @Override
    public void update(IStudent student) {
        if (student.ra().value() == null || student.id() == null) {
            throw new ResourceNotFoundException("student not found");
        }

        entityManager.createNativeQuery("DELETE FROM enrollments WHERE student_id = :studentId")
                .setParameter("studentId", student.id())
                .executeUpdate();

        var optional = StudentEntity.find("ra = :ra", Map.of("ra", student.ra().value()))
                .singleResultOptional();

        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("student not found");
        }

        var updatedStudent = (StudentEntity) optional.get();
        updatedStudent.setCr(student.cr().value());
        updatedStudent.setCp(student.cp().value());
        updatedStudent.setCourse(student.course());
        updatedStudent.setShift(student.shift());
        updatedStudent.cleanableAddAll(student.disciplines());
        updatedStudent.persist();
    }

    @Override
    public Optional<IStudent> findByRa(String ra) {
        var result = StudentEntity
                .find("ra = :ra", Map.of("ra", ra))
                .singleResultOptional();

        if (result.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(StudentEntity.toModel((StudentEntity) result.get()));
    }
}
