package org.ufabc.next.enrollmenteventtransmitter.infrastructure.student.repository;

import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.IStudent;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;

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
        entityManager.createNativeQuery("DELETE FROM enrollments WHERE student_id = " + student.id());
        StudentEntity.update("UPDATE students SET ra = :ra, name = :name, cr = :cr, cp = :cp WHERE ra = :ra",
                Map.of("ra", student.ra().toString(), "name", student.name(), "cr", student.cr().value(), "cp",
                        student.cp().value()));

        StringBuilder insertEnrolls = new StringBuilder("INSERT INTO enrollments (student_id, discipline_id) VALUES");
        for(IDiscipline discipline : student.disciplines()){
            insertEnrolls.append("(" + student.id() + "," + discipline.id() + ")");
        }
        entityManager.createNativeQuery(insertEnrolls.toString());
    }

    @Override
    public Optional<IStudent> findByRa(String ra) {
        var result = StudentEntity.find("ra", ra).firstResultOptional();

        if (result.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(StudentEntity.toModel((StudentEntity) result.get()));
    }

}
