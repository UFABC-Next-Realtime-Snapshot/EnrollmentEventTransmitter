package org.ufabc.next.enrollmenteventtransmitter.application.student.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.application.commons.events.IEventDispatcher;
import org.ufabc.next.enrollmenteventtransmitter.application.student.events.StudentRegisteredInDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.application.student.events.StudentRemovedFromDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.application.student.services.CalculateCoefficientsOfDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.enroll.EnrollStudent;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.enroll.InputEnrollStudent;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cp;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Discipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.DisciplineRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Professor;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentBuilder;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class EnrollStudentTest {
    public IEventDispatcher dispatcher = mock(IEventDispatcher.class);
    public StudentRepository studentRepository = mock(StudentRepository.class);
    public DisciplineRepository disciplineRepository = mock(DisciplineRepository.class);
    public CalculateCoefficientsOfDiscipline calculateCoefficientsOfDiscipline = mock(
            CalculateCoefficientsOfDiscipline.class);
    public EnrollStudent enrollStudent = new EnrollStudent(studentRepository, disciplineRepository,
            calculateCoefficientsOfDiscipline, dispatcher);

    @Test
    public void whenStudentExistsAndHasntEnrollmentsShouldReturnOutputEnrollStudent() {
        var student = new StudentBuilder(1L, "Some course", "SomeRa...", Shift.MORNING)
                .withCourse(new Course(1L, "Some course"))
                .withCp(1)
                .withCr(4)
                .withDisciplines(new ArrayList<>())
                .build();
        var discipline = Discipline.aDiscipline()
                .withId(1l)
                .withName("Some discipline")
                .withCode("Some code")
                .withCourse(new Course(1L, "Some course"))
                .withCP(new Cp(0))
                .withCR(new Cr(0))
                .withShift(Shift.MORNING)
                .withPracticeProfessor(new Professor("Some practice professor"))
                .withTheoryProfessor(new Professor("Some theory professor"))
                .withVacancies((short) 10)
                .build();

        when(studentRepository.findByRa("SomeRa...")).thenReturn(Optional.of(student));
        when(disciplineRepository.findByCode("Some code")).thenReturn(discipline);

        var disciplineCodes = new ArrayList<String>();
        disciplineCodes.add("Some code");
        var input = new InputEnrollStudent("SomeRa...", disciplineCodes);
        enrollStudent.execute(input);
        
        verify(studentRepository).update(any());
        verify(dispatcher).notify(new StudentRegisteredInDiscipline(discipline, any()));
        verify(calculateCoefficientsOfDiscipline).execute(discipline);
        verify(disciplineRepository).update(discipline);
    }

    @Test
    public void whenStudentExistsAndHasEnrollmentsShouldReturnOutputEnrollStudent() {
        var discipline = Discipline.aDiscipline()
                .withId(1l)
                .withName("Some discipline")
                .withCode("Some code")
                .withCourse(new Course(1L, "Some course"))
                .withCP(new Cp(0))
                .withCR(new Cr(0))
                .withShift(Shift.MORNING)
                .withPracticeProfessor(new Professor("Some practice professor"))
                .withTheoryProfessor(new Professor("Some theory professor"))
                .withVacancies((short) 10)
                .build();

        List<IDiscipline> studentDisciplines = List.of(discipline);
        var student = new StudentBuilder(1L, "Some course", "SomeRa...", Shift.MORNING)
                .withCourse(new Course(1L, "Some course"))
                .withCp(1)
                .withCr(4)
                .withDisciplines(studentDisciplines)
                .build();

        var otherDiscipline = Discipline.aDiscipline()
                .withId(2l)
                .withName("Other discipline")
                .withCode("Other code")
                .withCourse(new Course(1L, "Other course"))
                .withCP(new Cp(0))
                .withCR(new Cr(0))
                .withShift(Shift.MORNING)
                .withPracticeProfessor(new Professor("Other practice professor"))
                .withTheoryProfessor(new Professor("Other theory professor"))
                .withVacancies((short) 10)
                .build();

        when(studentRepository.findByRa("SomeRa...")).thenReturn(Optional.of(student));
        when(disciplineRepository.findByCode("Other code")).thenReturn(otherDiscipline);

        var disciplineCodes = new ArrayList<String>();
        disciplineCodes.add("Other code");
        var input = new InputEnrollStudent("SomeRa...", disciplineCodes);
        enrollStudent.execute(input);

        verify(studentRepository).update(any());
        verify(dispatcher).notify(isA(StudentRegisteredInDiscipline.class));
        verify(dispatcher).notify(isA(StudentRemovedFromDiscipline.class));
        verify(calculateCoefficientsOfDiscipline).execute(discipline);
        verify(calculateCoefficientsOfDiscipline).execute(otherDiscipline);
        verify(disciplineRepository).update(discipline);
        verify(disciplineRepository).update(otherDiscipline);
    }

    @Test
    public void whenStudentNotExistsShouldThrowsRuntimeException(){
        when(studentRepository.findByRa(anyString())).thenReturn(Optional.empty());
        var input = new InputEnrollStudent("SomeRa...", new ArrayList<>());
        
        Exception exception = assertThrows(RuntimeException.class, () -> {
            enrollStudent.execute(input);
        });

        assertEquals("pega aqui", exception.getMessage());
    }
}
