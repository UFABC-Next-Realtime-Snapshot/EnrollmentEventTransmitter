package org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Discipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Professor;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.IStudent;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentBuilder;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc.framework.JdbcTemplate;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc.framework.RowMapper;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class JdbcStudentRepositoryTest {
    @Inject
    StudentRepository studentRepository;

    @Inject
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void clean() {
        jdbcTemplate.update("DELETE FROM students", statement -> {
        });

        jdbcTemplate.update("DELETE FROM courses", statement -> {
        });

        jdbcTemplate.update("DELETE FROM enrollments", statement -> {
        });
    }

    @Test
    public void addStudentWithDisciplines() {
        String ra = "123";
        Course course = Course.BCT;

        var nonexistentStudent = studentRepository.findByRa(ra);

        assertFalse(nonexistentStudent.isPresent());

        var nonexistentCourse = findCourseByName(course.name());

        assertFalse(nonexistentCourse.isPresent());

        IStudent newStudent = new StudentBuilder("aName", ra, Shift.NIGHT)
                .withCr(4)
                .withCp(0.5F)
                .withCourse(course)
                .withDisciplines(List.of(
                        Discipline
                                .aDiscipline()
                                .withCode("aCode")
                                .withCourse(Course.BCT)
                                .withName("aName")
                                .withShift(Shift.MORNING)
                                .withCR(new Cr(3))
                                .withPracticeProfessor(new Professor("aPracticeProfessorPName"))
                                .withTheoryProfessor(new Professor("aTheoryProfessorName"))
                                .withVacancies((short) 10)
                                .build(),
                        Discipline
                                .aDiscipline()
                                .withCode("12345")
                                .withCourse(Course.BCT)
                                .withName("aName")
                                .withShift(Shift.NIGHT)
                                .withPracticeProfessor(new Professor("aPracticeProfessorPName"))
                                .withVacancies((short) 30)
                                .build()
                        )
                )
                .build();
        studentRepository.create(newStudent);

        var existentStudent = studentRepository.findByRa("123");

        assertTrue(existentStudent.isPresent());

        var existentCourse = findCourseByName(course.name());

        assertTrue(existentCourse.isPresent());
    }

    @Test
    public void addStudentWithoutDisciplines() {

    }

    private Optional<String> findCourseByName(String name) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT name FROM courses WHERE name = ?",
                (statement) -> statement.setString(1, name),
                new StringRowMapper()));
    }

    private static class StringRowMapper implements RowMapper<String> {

        @Override
        public String mapRow(ResultSet resultSet) throws SQLException {
            return resultSet.getString("name");
        }
    }

    private List<Enrollment> findAllByStudentId(Long id) {
        return jdbcTemplate.queryForList("SELECT student_id, discipline_id " +
                        "FROM enrollments WHERE student_id = ?",
                statement -> statement.setLong(1, id),
                new EnrollmentRowMapper());
    }

    private static class EnrollmentRowMapper implements RowMapper<Enrollment> {

        @Override
        public Enrollment mapRow(ResultSet resultSet) throws SQLException {
            return new Enrollment(resultSet.getLong("student_id"),
                    resultSet.getLong("discipline_id"));
        }
    }

    private static class Enrollment {
        private final Long studentId;
        private final Long disciplineId;

        public Enrollment(Long studentId, Long disciplineId) {
            this.studentId = studentId;
            this.disciplineId = disciplineId;
        }

        public Long studentId() {
            return studentId;
        }

        public Long disciplineId() {
            return disciplineId;
        }
    }
}
