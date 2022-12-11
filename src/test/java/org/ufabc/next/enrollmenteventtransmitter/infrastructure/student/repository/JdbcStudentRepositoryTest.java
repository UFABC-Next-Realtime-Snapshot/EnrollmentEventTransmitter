package org.ufabc.next.enrollmenteventtransmitter.infrastructure.student.repository;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.exceptions.ResourceNotFoundException;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Discipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Professor;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.IStudent;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentBuilder;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.repository.DisciplineEntity;
import org.ufabc.next.enrollmenteventtransmitter.util.Cleanable;
import org.ufabc.next.enrollmenteventtransmitter.util.CourseFixture;
import org.ufabc.next.enrollmenteventtransmitter.util.ProfessorFixture;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class JdbcStudentRepositoryTest extends Cleanable {

    @Inject
    StudentRepository studentRepository;

    @Test
    @Transactional
    public void addStudentWithDisciplinesAndFind() {
        String ra = "123";

        Course course = CourseFixture.aCourse();
        Professor practiceProfessor = ProfessorFixture.practiceProfessor();
        Professor theoryProfessor = ProfessorFixture.theoryProfessor();
        IDiscipline aDiscipline = aDisciplineWith(course, practiceProfessor, theoryProfessor);
        IDiscipline anotherDiscipline = anotherDisciplineWith(course, practiceProfessor);

        var nonexistentStudent = studentRepository.findByRa(ra);

        assertFalse(nonexistentStudent.isPresent());

        IStudent newStudent = new StudentBuilder(null, "aName", ra, Shift.NIGHT)
                .withCr(4)
                .withCp(0.5F)
                .withCourse(course)
                .withDisciplines(List.of(
                                aDiscipline,
                                anotherDiscipline
                        )
                )
                .build();
        studentRepository.add(newStudent);

        var optionalStudent = studentRepository.findByRa(ra);

        assertTrue(optionalStudent.isPresent());

        var existentStudent = optionalStudent.get();

        assertEquals(1L, existentStudent.id());
        assertEquals(newStudent.ra(), existentStudent.ra());
        assertEquals(newStudent.name(), existentStudent.name());
        assertEquals(newStudent.cp(), existentStudent.cp());
        assertEquals(newStudent.cr(), existentStudent.cr());
        assertEquals(newStudent.shift(), existentStudent.shift());
        assertEquals(newStudent.course().name(), existentStudent.course().name());
        assertEquals(newStudent.disciplines().size(), existentStudent.disciplines().size());
    }

    @Test
    @Transactional
    public void whenDisciplineWithIdAndRAIsNonexistentThenThrowResultNotFoundException() {
        Course course = CourseFixture.aCourse();
        Professor practiceProfessor = ProfessorFixture.practiceProfessor();
        Professor theoryProfessor = ProfessorFixture.theoryProfessor();
        IDiscipline aDiscipline = aDisciplineWith(course, practiceProfessor, theoryProfessor);

        IStudent newStudent = new StudentBuilder(1L, "aName", null, Shift.NIGHT)
                .withCr(4)
                .withCp(0.5F)
                .withCourse(course)
                .withDisciplines(List.of(
                                aDiscipline
                        )
                )
                .build();

        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> studentRepository.update(newStudent));

        String expectedMessage = "student not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Transactional
    public void whenStudentWithoutIdIsNonexistentThenThrowResultNotFoundException() {
        Course course = CourseFixture.aCourse();
        Professor practiceProfessor = ProfessorFixture.practiceProfessor();
        Professor theoryProfessor = ProfessorFixture.theoryProfessor();
        IDiscipline aDiscipline = aDisciplineWith(course, practiceProfessor, theoryProfessor);

        IStudent newStudent = new StudentBuilder(null, "aName", "aRA", Shift.NIGHT)
                .withCr(4)
                .withCp(0.5F)
                .withCourse(course)
                .withDisciplines(List.of(
                                aDiscipline
                        )
                )
                .build();

        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> studentRepository.update(newStudent));

        String expectedMessage = "student not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Transactional
    public void shouldUpdateStudent() {
        String ra = "123";

        Course course = CourseFixture.aCourse();
        Course anotherCourse = CourseFixture.anotherCourse();
        Professor practiceProfessor = ProfessorFixture.practiceProfessor();
        Professor theoryProfessor = ProfessorFixture.theoryProfessor();
        IDiscipline aDiscipline = aDisciplineWith(course, practiceProfessor, theoryProfessor);
        IDiscipline anotherDiscipline = anotherDisciplineWith(course, practiceProfessor);

        IStudent newStudent = new StudentBuilder(null, "aName", ra, Shift.NIGHT)
                .withCr(4)
                .withCp(0.5F)
                .withCourse(course)
                .withDisciplines(List.of(
                                aDiscipline,
                                anotherDiscipline
                        )
                )
                .build();
        studentRepository.add(newStudent);

        IStudent updatedStudent = new StudentBuilder(1L, "aName", ra, Shift.MORNING)
                .withCr(2)
                .withCp(1F)
                .withCourse(anotherCourse)
                .withDisciplines(List.of(
                                aDiscipline
                        )
                )
                .build();

        studentRepository.update(updatedStudent);

        var optionalStudent = studentRepository.findByRa(ra);

        assertTrue(optionalStudent.isPresent());

        var existentStudent = optionalStudent.get();

        assertEquals(1L, existentStudent.id());
        assertEquals(updatedStudent.cp(), existentStudent.cp());
        assertEquals(updatedStudent.cr(), existentStudent.cr());
        assertEquals(updatedStudent.shift(), existentStudent.shift());
        assertEquals(updatedStudent.course().name(), existentStudent.course().name());
        assertEquals(updatedStudent.disciplines().size(), existentStudent.disciplines().size());
    }

    private IDiscipline aDisciplineWith(Course course, Professor practiceProfessor, Professor theoryProfessor) {
        var disciplineEntity = DisciplineEntity.toEntity(Discipline
                .aDiscipline()
                .withCode("aCode")
                .withCourse(course)
                .withName("aName")
                .withShift(Shift.MORNING)
                .withCR(new Cr(3))
                .withPracticeProfessor(practiceProfessor)
                .withTheoryProfessor(theoryProfessor)
                .withVacancies((short) 10)
                .build()
        );

        disciplineEntity.persist();

        return DisciplineEntity.toModel(disciplineEntity);
    }

    private IDiscipline anotherDisciplineWith(Course course, Professor practiceProfessor) {
        var disciplineEntity = DisciplineEntity.toEntity(Discipline
                .aDiscipline()
                .withCode("12345")
                .withCourse(course)
                .withName("aName")
                .withShift(Shift.NIGHT)
                .withPracticeProfessor(practiceProfessor)
                .withVacancies((short) 30)
                .build()
        );

        disciplineEntity.persist();

        return DisciplineEntity.toModel(disciplineEntity);
    }
}