package org.ufabc.next.enrollmenteventtransmitter.application.student.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.create.CreateStudent;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.create.InputCreateStudent;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentBuilder;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CreateStudentTest {
    
    public final StudentRepository studentRepository = mock(StudentRepository.class);
    
    @Test
    public void whenStudentExistsShouldThrowsRuntimeException(){
        var student = new StudentBuilder(1L, "Some name", "Some Ra", Shift.MORNING).build();
        when(studentRepository.findByRa("Some Ra")).thenReturn(Optional.of(student));
        
        var input = new InputCreateStudent("Some name", "Some Ra", 0, 0, Shift.MORNING.initial());
        var createStudent = new CreateStudent(studentRepository);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            createStudent.execute(input);
        });

        assertEquals("Deu ruim", exception.getMessage());
        verify(studentRepository).findByRa("Some Ra");
    }

    @Test
    public void whenStudentExistsShouldReturnOutputCreateStudent(){
        when(studentRepository.findByRa("Some Ra")).thenReturn(Optional.empty());
        
        var input = new InputCreateStudent("Some name", "Some Ra", 0, 0, Shift.MORNING.initial());
        var createStudent = new CreateStudent(studentRepository);
        createStudent.execute(input);

        verify(studentRepository).findByRa("Some Ra");
        verify(studentRepository).add(any());
    }
}
