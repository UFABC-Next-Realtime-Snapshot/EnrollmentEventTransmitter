package org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.repository;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.exceptions.ResourceNotFoundException;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cp;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Discipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.DisciplineRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Professor;
import org.ufabc.next.enrollmenteventtransmitter.util.Cleanable;
import org.ufabc.next.enrollmenteventtransmitter.util.CourseFixture;
import org.ufabc.next.enrollmenteventtransmitter.util.ProfessorFixture;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class JdbcDisciplineRepositoryTest extends Cleanable {

    @Inject
    DisciplineRepository disciplineRepository;

    @Test
    @Transactional
    public void shouldAddAndFind() {
        String code = "aCode";

        Course course = CourseFixture.aCourse();
        Professor practiceProfessor = ProfessorFixture.practiceProfessor();
        Professor theoryProfessor = ProfessorFixture.theoryProfessor();

        IDiscipline discipline = Discipline
                .aDiscipline()
                .withCode(code)
                .withCourse(course)
                .withName("aName")
                .withShift(Shift.MORNING)
                .withCR(new Cr(3))
                .withPracticeProfessor(practiceProfessor)
                .withTheoryProfessor(theoryProfessor)
                .withVacancies((short) 10)
                .build();

        disciplineRepository.add(discipline);

        var optionalSavedDiscipline = disciplineRepository.findByCode(code);

        assertTrue(optionalSavedDiscipline.isPresent());

        var savedDiscipline = optionalSavedDiscipline.get();

        assertEquals(1, savedDiscipline.id());
        assertEquals(discipline.code(), savedDiscipline.code());
        assertEquals(discipline.practiceProfessor().name(), savedDiscipline.practiceProfessor().name());
        assertEquals(discipline.theoryProfessor().name(), savedDiscipline.theoryProfessor().name());
        assertEquals(discipline.name(), savedDiscipline.name());
        assertEquals(discipline.vacancies(), savedDiscipline.vacancies());
        assertEquals(discipline.shift(), savedDiscipline.shift());
        assertEquals(discipline.thresholdCr(), savedDiscipline.thresholdCr());
        assertEquals(discipline.thresholdCp(), savedDiscipline.thresholdCp());
        assertEquals(discipline.course().name(), savedDiscipline.course().name());
        assertEquals(discipline.subscribers(), savedDiscipline.subscribers());
    }

    @Test
    @Transactional
    public void shouldNotFind() {
        var discipline = disciplineRepository.findByCode("aCode");

        assertFalse(discipline.isPresent());
    }

    @Test
    @Transactional
    public void shouldFindAll() {
        Course course = CourseFixture.aCourse();
        Professor practiceProfessor = ProfessorFixture.practiceProfessor();
        Professor theoryProfessor = ProfessorFixture.theoryProfessor();

        List<IDiscipline> disciplines = List.of(Discipline
                        .aDiscipline()
                        .withCode("aCode")
                        .withCourse(course)
                        .withName("aName")
                        .withShift(Shift.MORNING)
                        .withCR(new Cr(3))
                        .withPracticeProfessor(practiceProfessor)
                        .withTheoryProfessor(theoryProfessor)
                        .withVacancies((short) 10)
                        .build(),
                Discipline
                        .aDiscipline()
                        .withCode("anotherCode")
                        .withCourse(course)
                        .withName("aName")
                        .withShift(Shift.MORNING)
                        .withCR(new Cr(3))
                        .withCP(new Cp(1))
                        .withTheoryProfessor(theoryProfessor)
                        .withVacancies((short) 10)
                        .build()
        );

        disciplines.forEach(discipline -> disciplineRepository.add(discipline));

        var savedDisciplines = disciplineRepository.findAll();

        assertEquals(disciplines.size(), savedDisciplines.size());
    }

    @Test
    @Transactional
    public void shouldUpdate() {
        String code = "aCode";
        Long id = 1L;

        Course course = CourseFixture.aCourse();
        Professor practiceProfessor = ProfessorFixture.practiceProfessor();
        Professor theoryProfessor = ProfessorFixture.theoryProfessor();

        IDiscipline discipline = Discipline
                .aDiscipline()
                .withCode(code)
                .withCourse(course)
                .withName("aName")
                .withShift(Shift.MORNING)
                .withCR(new Cr(3))
                .withPracticeProfessor(practiceProfessor)
                .withTheoryProfessor(theoryProfessor)
                .withVacancies((short) 10)
                .build();

        disciplineRepository.add(discipline);

        var optionalSavedDiscipline = disciplineRepository.findByCode(code);

        assertTrue(optionalSavedDiscipline.isPresent());

        var savedDiscipline = optionalSavedDiscipline.get();

        assertEquals(id, savedDiscipline.id());
        assertEquals(discipline.code(), savedDiscipline.code());
        assertEquals(discipline.practiceProfessor().name(), savedDiscipline.practiceProfessor().name());
        assertEquals(discipline.theoryProfessor().name(), savedDiscipline.theoryProfessor().name());
        assertEquals(discipline.name(), savedDiscipline.name());
        assertEquals(discipline.vacancies(), savedDiscipline.vacancies());
        assertEquals(discipline.shift(), savedDiscipline.shift());
        assertEquals(discipline.thresholdCr(), savedDiscipline.thresholdCr());
        assertEquals(discipline.thresholdCp(), savedDiscipline.thresholdCp());
        assertEquals(discipline.course().name(), savedDiscipline.course().name());
        assertEquals(discipline.subscribers(), savedDiscipline.subscribers());

        var updatedDiscipline = Discipline
                .aDiscipline()
                .withId(id)
                .withCode(code)
                .withCourse(course)
                .withName("aName")
                .withShift(Shift.MORNING)
                .withCR(new Cr(4))
                .withCP(new Cp(0.5F))
                .withPracticeProfessor(practiceProfessor)
                .withTheoryProfessor(theoryProfessor)
                .withVacancies((short) 10)
                .build();

        disciplineRepository.update(updatedDiscipline);

        var optionalUpdatedDiscipline = disciplineRepository.findByCode(code);

        assertTrue(optionalUpdatedDiscipline.isPresent());

        var dbUpdatedDiscipline = optionalUpdatedDiscipline.get();

        assertEquals(id, updatedDiscipline.id());
        assertEquals(updatedDiscipline.thresholdCr(), dbUpdatedDiscipline.thresholdCr());
        assertEquals(updatedDiscipline.thresholdCp(), dbUpdatedDiscipline.thresholdCp());
        assertEquals(0, dbUpdatedDiscipline.subscribers());
    }

    @Test
    @Transactional
    public void whenDisciplineWithIdIsNonexistentThenThrowResultNotFoundException() {
        Course course = CourseFixture.aCourse();
        Professor practiceProfessor = ProfessorFixture.practiceProfessor();
        Professor theoryProfessor = ProfessorFixture.theoryProfessor();

        IDiscipline discipline = Discipline
                .aDiscipline()
                .withId(1L)
                .withCode("aCode")
                .withCourse(course)
                .withName("aName")
                .withShift(Shift.MORNING)
                .withCR(new Cr(3))
                .withPracticeProfessor(practiceProfessor)
                .withTheoryProfessor(theoryProfessor)
                .withVacancies((short) 10)
                .build();
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> disciplineRepository.update(discipline));

        String expectedMessage = "Discipline aCode is nonexistent";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Transactional
    public void whenDisciplineWithoutIdIsNonexistentThenThrowResultNotFoundException() {
        Course course = CourseFixture.aCourse();
        Professor practiceProfessor = ProfessorFixture.practiceProfessor();
        Professor theoryProfessor = ProfessorFixture.theoryProfessor();

        IDiscipline discipline = Discipline
                .aDiscipline()
                .withCode("aCode")
                .withCourse(course)
                .withName("aName")
                .withShift(Shift.MORNING)
                .withCR(new Cr(3))
                .withPracticeProfessor(practiceProfessor)
                .withTheoryProfessor(theoryProfessor)
                .withVacancies((short) 10)
                .build();
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> disciplineRepository.update(discipline));

        String expectedMessage = "Discipline aCode is nonexistent";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
