package org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc;

import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Discipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.DisciplineRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.Student;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc.framework.JdbcTemplate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class jdbcDisciplineRepository implements DisciplineRepository {

    @Inject
    JdbcTemplate jdbcTemplate;

    @Override
    public void addDiscipline(Discipline discipline) {
        jdbcTemplate.update("INSERT INTO discipline (code, name, cr, cp, shift, vacancies, " +
                        "course_id, theory_professor_id, practice_professor_id) " +
                        "VALUES (?, ?, ?, ?, ?, (SELECT id FROM courses WHERE name = ?));",
                statement -> {
                    statement.setString(1,discipline.code());
                    statement.setString(2, discipline.name());
                    statement.setFloat(3, discipline.thresholdCr().value());
                    statement.setFloat(4, discipline.thresholdCp().value());
                    statement.setString(5, String.valueOf(discipline.shift().initial()));
                    statement.setInt(6, discipline.vacancies());
                    statement.setLong(7, 1);
                    statement.setLong(8, 1);
                    statement.setLong(9, 1);
                });
    }

    @Override
    public Discipline updateDiscipline(Discipline discipline) {
        return null;
    }

    @Override
    public Discipline findDisciplineByCode(String code) {
        return null;
    }

    @Override
    public List<Discipline> findAllDisciplines() {
        return null;
    }

    @Override
    public List<Discipline> findDisciplinesByStudent(Student student) {
        return null;
    }
}
