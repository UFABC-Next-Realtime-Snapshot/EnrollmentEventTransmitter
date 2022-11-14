package org.ufabc.next.enrollmenteventtransmitter.infrastructure.student.repository;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Discipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Professor;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.IStudent;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentBuilder;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.commons.repository.CourseEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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
    
    @Test
    @Transactional
    public void addStudentWithDisciplines() {
        String ra = "123";
        
        var courseEntity = CourseEntity.toEntity(new Course(null, "Course"));
        courseEntity.persist();

        assertTrue(courseEntity.isPersistent());

        Course course = CourseEntity.toModel(courseEntity);

        var nonexistentStudent = studentRepository.findByRa(ra);

        assertFalse(nonexistentStudent.isPresent());

        IStudent newStudent = new StudentBuilder(1L, "aName", ra, Shift.NIGHT)
                .withCr(4)
                .withCp(0.5F)
                .withCourse(course)
                .withDisciplines(List.of(
                        Discipline
                                .aDiscipline()
                                .withId(1L)
                                .withCode("aCode")
                                .withCourse(course)
                                .withName("aName")
                                .withShift(Shift.MORNING)
                                .withCR(new Cr(3))
                                .withPracticeProfessor(new Professor("aPracticeProfessorPName"))
                                .withTheoryProfessor(new Professor("aTheoryProfessorName"))
                                .withVacancies((short) 10)
                                .build(),
                        Discipline
                                .aDiscipline()
                                .withId(2L)
                                .withCode("12345")
                                .withCourse(course)
                                .withName("aName")
                                .withShift(Shift.NIGHT)
                                .withPracticeProfessor(new Professor("aPracticeProfessorPName"))
                                .withVacancies((short) 30)
                                .build()
                        )
                )
                .build();
        studentRepository.add(newStudent);

        var existentStudent = studentRepository.findByRa("123");

        assertTrue(existentStudent.isPresent());
    }
}