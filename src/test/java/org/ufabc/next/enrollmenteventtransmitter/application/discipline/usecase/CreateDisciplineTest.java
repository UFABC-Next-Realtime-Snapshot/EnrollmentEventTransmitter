package org.ufabc.next.enrollmenteventtransmitter.application.discipline.usecase;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.exceptions.ResourceNotFoundException;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.CourseRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@QuarkusTest
public class CreateDisciplineTest {
    private final DisciplineRepository disciplineRepository = mock(DisciplineRepository.class);
    private final CourseRepository courseRepository = mock(CourseRepository.class);
    private final ProfessorRepository professorRepository = mock(ProfessorRepository.class);
    private final CreateDiscipline createDiscipline = new CreateDiscipline(disciplineRepository,
            courseRepository, professorRepository);

    @Test
    public void shouldCreateWhenCourseExistAndProfessorsExist() {
        String courseName = "aName";
        String practicalProfessorName = "aName";
        String theoryProfessorName = "anotherName";
        Course aCourse = new Course(1L, courseName);
        Professor practicalProfessor = new Professor(practicalProfessorName);
        Professor theoryProfessor = new Professor(theoryProfessorName);
        IDiscipline discipline = Discipline
                .aDiscipline()
                .withCourse(aCourse)
                .withPracticeProfessor(practicalProfessor)
                .withTheoryProfessor(theoryProfessor)
                .withName("aName")
                .withCode("aCode")
                .withShift(Shift.MORNING)
                .build();

        when(disciplineRepository.findByCode(any())).thenReturn(Optional.empty());
        when(courseRepository.findByName(courseName)).thenReturn(Optional.of(aCourse));
        when(professorRepository.findByName(practicalProfessorName)).thenReturn(Optional.of(practicalProfessor));
        when(professorRepository.findByName(theoryProfessorName)).thenReturn(Optional.of(theoryProfessor));

        createDiscipline.execute(discipline);

        verify(disciplineRepository).add(discipline);
    }

    @Test
    public void shouldCreateWhenCourseExistAndPracticalProfessorsExistAndTheoryProfessorIsNull() {
        String courseName = "aName";
        String practicalProfessorName = "aName";
        Course aCourse = new Course(1L, courseName);
        Professor practicalProfessor = new Professor(practicalProfessorName);
        IDiscipline discipline = Discipline
                .aDiscipline()
                .withCourse(aCourse)
                .withPracticeProfessor(practicalProfessor)
                .withName("aName")
                .withCode("aCode")
                .withShift(Shift.MORNING)
                .build();

        when(disciplineRepository.findByCode(any())).thenReturn(Optional.empty());
        when(courseRepository.findByName(courseName)).thenReturn(Optional.of(aCourse));
        when(professorRepository.findByName(practicalProfessorName)).thenReturn(Optional.of(practicalProfessor));

        createDiscipline.execute(discipline);

        verify(disciplineRepository).add(discipline);
        verify(professorRepository, never()).add(any());
    }

    @Test
    public void shouldCreateWhenCourseExistAndPracticalProfessorsNotExistAndTheoryProfessorIsNull() {
        String courseName = "aName";
        String practicalProfessorName = "aName";
        Course aCourse = new Course(1L, courseName);
        Professor practicalProfessor = new Professor(practicalProfessorName);
        Professor savedPracticalProfessor = new Professor(1L, practicalProfessorName);
        IDiscipline discipline = Discipline
                .aDiscipline()
                .withCourse(aCourse)
                .withPracticeProfessor(practicalProfessor)
                .withName("aName")
                .withCode("aCode")
                .withShift(Shift.MORNING)
                .build();

        when(disciplineRepository.findByCode(any())).thenReturn(Optional.empty());
        when(courseRepository.findByName(courseName)).thenReturn(Optional.of(aCourse));
        when(professorRepository.findByName(practicalProfessorName)).thenReturn(Optional.empty());
        when(professorRepository.add(practicalProfessor)).thenReturn(savedPracticalProfessor);

        createDiscipline.execute(discipline);

        verify(disciplineRepository).add(Discipline
                .aDiscipline()
                .withCourse(aCourse)
                .withPracticeProfessor(savedPracticalProfessor)
                .withName("aName")
                .withCode("aCode")
                .withShift(Shift.MORNING)
                .build());
        verify(professorRepository).add(practicalProfessor);
    }

    @Test
    public void shouldCreateWhenCourseExistAndPracticalProfessorsIsNullAndTheoryProfessorExist() {
        String courseName = "aName";
        String theoryProfessorName = "anotherName";
        Course aCourse = new Course(1L, courseName);
        Professor theoryProfessor = new Professor(theoryProfessorName);
        IDiscipline discipline = Discipline
                .aDiscipline()
                .withCourse(aCourse)
                .withTheoryProfessor(theoryProfessor)
                .withName("aName")
                .withCode("aCode")
                .withShift(Shift.MORNING)
                .build();

        when(disciplineRepository.findByCode(any())).thenReturn(Optional.empty());
        when(courseRepository.findByName(courseName)).thenReturn(Optional.of(aCourse));
        when(professorRepository.findByName(theoryProfessorName)).thenReturn(Optional.of(theoryProfessor));

        createDiscipline.execute(discipline);

        verify(disciplineRepository).add(discipline);
        verify(professorRepository, never()).add(any());
    }

    @Test
    public void shouldCreateWhenCourseExistAndPracticalProfessorsIsNullAndTheoryProfessorNotExist() {
        String courseName = "aName";
        String theoryProfessorName = "aName";
        Course aCourse = new Course(1L, courseName);
        Professor theoryProfessor = new Professor(theoryProfessorName);
        Professor savedTheoryProfessor = new Professor(1L, theoryProfessorName);
        IDiscipline discipline = Discipline
                .aDiscipline()
                .withCourse(aCourse)
                .withTheoryProfessor(theoryProfessor)
                .withName("aName")
                .withCode("aCode")
                .withShift(Shift.MORNING)
                .build();

        when(disciplineRepository.findByCode(any())).thenReturn(Optional.empty());
        when(courseRepository.findByName(courseName)).thenReturn(Optional.of(aCourse));
        when(professorRepository.findByName(theoryProfessorName)).thenReturn(Optional.empty());
        when(professorRepository.add(theoryProfessor)).thenReturn(savedTheoryProfessor);

        createDiscipline.execute(discipline);

        verify(disciplineRepository).add(Discipline
                .aDiscipline()
                .withCourse(aCourse)
                .withTheoryProfessor(savedTheoryProfessor)
                .withName("aName")
                .withCode("aCode")
                .withShift(Shift.MORNING)
                .build());
        verify(professorRepository).add(theoryProfessor);
    }

    @Test
    public void whenDisciplineExistMustBeDoNothing() {
        String code = "aCode";
        String courseName = "aName";
        String practicalProfessorName = "aName";
        String theoryProfessorName = "anotherName";
        Course aCourse = new Course(1L, courseName);
        Professor practicalProfessor = new Professor(practicalProfessorName);
        Professor theoryProfessor = new Professor(theoryProfessorName);
        IDiscipline discipline = Discipline
                .aDiscipline()
                .withCourse(aCourse)
                .withPracticeProfessor(practicalProfessor)
                .withTheoryProfessor(theoryProfessor)
                .withName("aName")
                .withCode(code)
                .withShift(Shift.MORNING)
                .build();

        when(disciplineRepository.findByCode(code)).thenReturn(Optional.of(discipline));

        createDiscipline.execute(discipline);

        verify(disciplineRepository, never()).add(discipline);
    }

    @Test
    public void whenCourseNoExistMustThrowException() {
        String code = "aCode";
        String courseName = "aName";
        String practicalProfessorName = "aName";
        String theoryProfessorName = "anotherName";
        Course aCourse = new Course(1L, courseName);
        Professor practicalProfessor = new Professor(practicalProfessorName);
        Professor theoryProfessor = new Professor(theoryProfessorName);
        IDiscipline discipline = Discipline
                .aDiscipline()
                .withCourse(aCourse)
                .withPracticeProfessor(practicalProfessor)
                .withTheoryProfessor(theoryProfessor)
                .withName("aName")
                .withCode(code)
                .withShift(Shift.MORNING)
                .build();

        when(disciplineRepository.findByCode(code)).thenReturn(Optional.empty());
        when(courseRepository.findByName(courseName)).thenReturn(Optional.empty());
        when(professorRepository.findByName(practicalProfessorName)).thenReturn(Optional.of(practicalProfessor));
        when(professorRepository.findByName(theoryProfessorName)).thenReturn(Optional.of(theoryProfessor));

        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> createDiscipline.execute(discipline));

        String expectedMessage = "Course aName not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        verify(disciplineRepository, never()).add(discipline);
    }
}
