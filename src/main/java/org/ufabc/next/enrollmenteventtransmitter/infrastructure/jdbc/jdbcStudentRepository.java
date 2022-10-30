package org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc;

import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Discipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.IStudent;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc.framework.JdbcTemplate;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc.framework.TransactionOperation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.JDBCType;
import java.util.Optional;

@ApplicationScoped
public class jdbcStudentRepository implements StudentRepository {

    @Inject
    JdbcTemplate jdbcTemplate;

    @Override
    public void create(IStudent student) {
        jdbcTemplate.transactionUpdates(
                addCourseIfNoExist(student),
                addStudent(student),
                addStudentInDisciplines(student)
        );
    }

    private TransactionOperation addCourseIfNoExist(IStudent student) {
        return new TransactionOperation("INSERT INTO courses (name) " +
                "SELECT * FROM (SELECT CAST(? AS VARCHAR) AS fake_name) fake " +
                "WHERE NOT EXISTS " +
                "(SELECT name FROM courses WHERE name = ?)",
                statement -> {
                    statement.setString(1, student.course().name());
                    statement.setString(2, student.course().name());
                });
    }

    private TransactionOperation addStudent(IStudent student) {
        return new TransactionOperation("INSERT INTO students (ra, name, cr, cp, shift, course_id) " +
                "VALUES (?, ?, ?, ?, ?, (SELECT id FROM courses WHERE name = ?));",
                statement -> {
                    statement.setString(1, student.ra().value());
                    statement.setString(2, student.name());
                    statement.setFloat(3, student.cr().value());
                    statement.setFloat(4, student.cp().value());
                    statement.setString(5, String.valueOf(student.shift().initial()));
                    statement.setString(6, student.course().name());
                });
    }

    private TransactionOperation addStudentInDisciplines(IStudent student) {
        return new TransactionOperation("INSERT INTO enrollments (student_id, discipline_id) " +
                "SELECT s.id, d.id FROM disciplines d " +
                "cross join ((SELECT id FROM students WHERE ra = ?)) as s " +
                "WHERE d.code IN (?);",
                statement -> {
                    statement.setString(1, student.ra().value());
                    var arrayOfParams = statement
                            .getConnection()
                            .createArrayOf(JDBCType.VARCHAR.getName(), student.disciplines().stream()
                                    .map(Discipline::code)
                                    .toArray(String[]::new));
                    statement.setArray(2, arrayOfParams);
                });
    }

    @Override
    public void update(IStudent student) {
        jdbcTemplate.update("UPDATE students SET WHERE ra = ?",
                statement -> {
                });
    }

    @Override
    public Optional<IStudent> findByRa(String ra) {
        return Optional.ofNullable(jdbcTemplate
                .queryForObject("SELECT s.ra, s.name, s.cr, s.cp, s.shift, c.name as course_name " +
                                "FROM students s INNER JOIN courses c ON c.id = s.course_id " +
                                "WHERE ra = ?",
                        statement -> statement.setString(1, ra),
                        new StudentRowMapper()
                )
        );
    }
}
