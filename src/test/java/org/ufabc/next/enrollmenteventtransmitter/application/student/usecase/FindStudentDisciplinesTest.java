package org.ufabc.next.enrollmenteventtransmitter.application.student.usecase;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.find.FindStudentDisciplines;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.exceptions.ResourceNotFoundException;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cp;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Discipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Professor;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentBuilder;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
public class FindStudentDisciplinesTest {

    private final StudentRepository studentRepository = mock(StudentRepository.class);
    private final FindStudentDisciplines findStudentDisciplines = new FindStudentDisciplines(studentRepository);

    @Test
    public void whenRAIsNullMustThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> findStudentDisciplines.execute(null));

        String expectedMessage = "ra is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        verify(studentRepository, never()).findByRa(any());
    }

    @Test
    public void whenStudentNotExistMustThrowException() {
        String ra = "someRA";
        when(studentRepository.findByRa(ra)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> findStudentDisciplines.execute(ra));

        String expectedMessage = "student not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenStudentExistMustReturnStudentDisciplines() {
        String ra = "someRA";

        when(studentRepository.findByRa(ra)).thenReturn(Optional
                .of(new StudentBuilder(1L, "Some name", "SomeRA", Shift.MORNING)
                        .withCourse(new Course(1L, "Some course"))
                        .withCp(1)
                        .withCr(4)
                        .withDisciplines(List.of(
                                Discipline.aDiscipline()
                                        .withName("anotherName")
                                        .withCode("anotherCode")
                                        .withCourse(new Course("name"))
                                        .withCP(new Cp(0.5F))
                                        .withCR(new Cr(2.4F))
                                        .withShift(Shift.MORNING)
                                        .withPracticeProfessor(new Professor("name"))
                                        .withVacancies((short) 50)
                                        .build(),
                                Discipline.aDiscipline()
                                        .withName("anotherName")
                                        .withCode("anotherCode")
                                        .withCourse(new Course("name"))
                                        .withCP(new Cp(0.5F))
                                        .withCR(new Cr(2.4F))
                                        .withShift(Shift.MORNING)
                                        .withPracticeProfessor(new Professor("name"))
                                        .withTheoryProfessor(new Professor("name"))
                                        .withVacancies((short) 50)
                                        .build()
                        ))
                        .build()));

        var disciplines = findStudentDisciplines.execute(ra);

        assertEquals(2, disciplines.size());
    }
}
