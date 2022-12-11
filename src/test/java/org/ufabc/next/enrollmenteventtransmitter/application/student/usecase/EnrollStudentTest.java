package org.ufabc.next.enrollmenteventtransmitter.application.student.usecase;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.application.student.events.StudentEventDispatcher;
import org.ufabc.next.enrollmenteventtransmitter.application.student.events.StudentRegisteredInDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.application.student.events.StudentRemovedFromDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.application.student.services.CalculateCoefficientsOfDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.enroll.EnrollStudent;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.enroll.InputEnrollStudent;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cp;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.CourseRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Discipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.DisciplineRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Professor;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.IStudent;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentBuilder;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@QuarkusTest
public class EnrollStudentTest {
    private final StudentEventDispatcher dispatcher = mock(StudentEventDispatcher.class);
    private final StudentRepository studentRepository = mock(StudentRepository.class);
    private final DisciplineRepository disciplineRepository = mock(DisciplineRepository.class);
    private final CourseRepository courseRepository = mock(CourseRepository.class);
    private final CalculateCoefficientsOfDiscipline calculateCoefficientsOfDiscipline = mock(
            CalculateCoefficientsOfDiscipline.class);
    private final EnrollStudent enrollStudent = new EnrollStudent(
            disciplineRepository,
            studentRepository,
            courseRepository,
            calculateCoefficientsOfDiscipline,
            dispatcher);

    @Test
    public void whenStudentExistsAndHasntEnrollmentsShouldReturnOutputEnrollStudent() {
        var course = new Course(1L, "Some course");

        var student = new StudentBuilder(1L, "Some name", "SomeRa...", Shift.MORNING)
                .withCourse(course)
                .withCp(1)
                .withCr(4)
                .withDisciplines(new ArrayList<>())
                .build();

        var discipline = Discipline.aDiscipline()
                .withId(1L)
                .withName("Some discipline")
                .withCode("Some code")
                .withCourse(course)
                .withCP(new Cp(0))
                .withCR(new Cr(0))
                .withShift(Shift.MORNING)
                .withPracticeProfessor(new Professor("Some practice professor"))
                .withTheoryProfessor(new Professor("Some theory professor"))
                .withVacancies((short) 10)
                .build();

        when(courseRepository.findByName("Some course")).thenReturn(Optional.of(course));
        when(studentRepository.findByRa("SomeRa...")).thenReturn(Optional.of(student));
        when(disciplineRepository.findByCode("Some code")).thenReturn(Optional.of(discipline));

        var disciplineCodes = new ArrayList<String>();
        disciplineCodes.add("Some code");
        var input = new InputEnrollStudent(
                "SomeRa...", "Some name", "Some course",
                4F, 1F, Shift.MORNING.initial(), disciplineCodes);
        enrollStudent.execute(input);

        verify(studentRepository).update(any());
        verify(courseRepository).findByName("Some course");
        verify(dispatcher).notify(new StudentRegisteredInDiscipline(discipline, any()));
        verify(calculateCoefficientsOfDiscipline).execute(discipline);
        verify(disciplineRepository).update(discipline);
    }

    @Test
    public void whenStudentNotExistsShouldReturnOutputEnrollStudent() {
        var course = new Course(1L, "Some course");

        var discipline = Discipline.aDiscipline()
                .withId(1L)
                .withName("Some discipline")
                .withCode("Some code")
                .withCourse(course)
                .withCP(new Cp(0))
                .withCR(new Cr(0))
                .withShift(Shift.MORNING)
                .withPracticeProfessor(new Professor("Some practice professor"))
                .withTheoryProfessor(new Professor("Some theory professor"))
                .withVacancies((short) 10)
                .build();

        when(courseRepository.findByName("Some course")).thenReturn(Optional.of(course));
        when(studentRepository.findByRa("SomeRa...")).thenReturn(Optional.empty());
        when(disciplineRepository.findByCode("Some code")).thenReturn(Optional.of(discipline));

        var disciplineCodes = new ArrayList<String>();
        disciplineCodes.add("Some code");
        var input = new InputEnrollStudent(
                "SomeRa...", "Some name", "Some course",
                4F, 1F, Shift.MORNING.initial(), disciplineCodes);
        enrollStudent.execute(input);

        verify(studentRepository).findByRa("SomeRa...");
        verify(studentRepository).add(any(IStudent.class));
        verify(courseRepository).findByName("Some course");
        verify(dispatcher).notify(new StudentRegisteredInDiscipline(discipline, any()));
        verify(calculateCoefficientsOfDiscipline).execute(discipline);
        verify(disciplineRepository).update(discipline);
    }

    @Test
    public void whenStudentExistsAndHasEnrollmentsShouldReturnOutputEnrollStudent() {
        var course = new Course(1L, "Other course");
        var discipline = Discipline.aDiscipline()
                .withId(1L)
                .withName("Some discipline")
                .withCode("Some code")
                .withCourse(course)
                .withCP(new Cp(0))
                .withCR(new Cr(0))
                .withShift(Shift.MORNING)
                .withPracticeProfessor(new Professor("Some practice professor"))
                .withTheoryProfessor(new Professor("Some theory professor"))
                .withVacancies((short) 10)
                .build();

        List<IDiscipline> studentDisciplines = List.of(discipline);
        var student = new StudentBuilder(1L, "Some name", "SomeRa...", Shift.MORNING)
                .withCourse(course)
                .withCp(1)
                .withCr(4)
                .withDisciplines(studentDisciplines)
                .build();

        var otherDiscipline = Discipline.aDiscipline()
                .withId(2L)
                .withName("Other discipline")
                .withCode("Other code")
                .withCourse(course)
                .withCP(new Cp(0))
                .withCR(new Cr(0))
                .withShift(Shift.MORNING)
                .withPracticeProfessor(new Professor("Other practice professor"))
                .withTheoryProfessor(new Professor("Other theory professor"))
                .withVacancies((short) 10)
                .build();

        when(courseRepository.findByName("Some course")).thenReturn(Optional.of(course));
        when(studentRepository.findByRa("SomeRa...")).thenReturn(Optional.of(student));
        when(disciplineRepository.findByCode("Other code")).thenReturn(Optional.of(otherDiscipline));

        var disciplineCodes = new ArrayList<String>();
        disciplineCodes.add("Other code");
        var input = new InputEnrollStudent(
                "SomeRa...", "Some name", "Some course", 4F, 1F,
                Shift.MORNING.initial(), disciplineCodes);
        enrollStudent.execute(input);

        verify(courseRepository).findByName("Some course");
        verify(studentRepository).update(any());
        verify(disciplineRepository).findByCode("Other code");
        verify(dispatcher).notify(isA(StudentRegisteredInDiscipline.class));
        verify(dispatcher).notify(isA(StudentRemovedFromDiscipline.class));
        verify(calculateCoefficientsOfDiscipline).execute(discipline);
        verify(calculateCoefficientsOfDiscipline).execute(otherDiscipline);
        verify(disciplineRepository).update(discipline);
        verify(disciplineRepository).update(otherDiscipline);
    }

    @Test
    public void whenSomeDisciplineNotExistsShouldThrowsRuntimeException() {
        when(courseRepository.findByName("Some Course")).thenReturn(Optional.of(new Course(1L, "Some Course")));
        when(disciplineRepository.findByCode(anyString())).thenReturn(Optional.empty());
        var input = new InputEnrollStudent("SomeRa...", "Some name", "Some course", 4F,
                1F, Shift.MORNING.initial(), List.of("Some code"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            enrollStudent.execute(input);
        });

        assertEquals("discipline code: Some code not exists", exception.getMessage());
    }

    @Test
    public void whenCourseNotExistsShouldThrowsRuntimeException() {
        when(courseRepository.findByName("Some Course")).thenReturn(Optional.empty());
        var input = new InputEnrollStudent("SomeRa...", "Some name", "Some course", 4F,
                1F, Shift.MORNING.initial(), new ArrayList<>());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            enrollStudent.execute(input);
        });

        assertEquals("course: Some course not exists", exception.getMessage());
    }
}
