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
import org.ufabc.next.enrollmenteventtransmitter.domain.course.CourseRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentBuilder;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;
import org.ufabc.next.enrollmenteventtransmitter.util.CourseFixture;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CreateStudentTest {
    
    private final StudentRepository studentRepository = mock(StudentRepository.class);
    private final CourseRepository courseRepository = mock(CourseRepository.class);
    private final CreateStudent createStudent = new CreateStudent(studentRepository, courseRepository);
    
    @Test
    public void whenStudentExistsShouldThrowsRuntimeException(){
        var student = new StudentBuilder(1L, "Some name", "Some Ra", Shift.MORNING).build();
        when(studentRepository.findByRa("Some Ra")).thenReturn(Optional.of(student));
        
        var input = new InputCreateStudent("Some name", "Some Ra", "BCC", 0, 0, Shift.MORNING.initial());
        
        Exception exception = assertThrows(RuntimeException.class, () -> {
            createStudent.execute(input);
        });

        assertEquals("O estudante ja existe", exception.getMessage());
        verify(studentRepository).findByRa("Some Ra");
    }

    @Test
    public void whenCourseNotExistsShouldThrowsRuntimeException(){
        when(studentRepository.findByRa("Some Ra")).thenReturn(Optional.empty());
        when(courseRepository.findByName("BCC")).thenReturn(Optional.empty());
        
        var input = new InputCreateStudent("Some name", "Some Ra", "BCC", 0, 0, Shift.MORNING.initial());
        
        Exception exception = assertThrows(RuntimeException.class, () -> {
            createStudent.execute(input);
        });

        assertEquals("Nao achei o curso", exception.getMessage());
        verify(studentRepository).findByRa("Some Ra");
    }

    @Test
    public void shouldReturnOutputCreateStudent(){
        var course = CourseFixture.aCourse();
        when(studentRepository.findByRa("Some Ra")).thenReturn(Optional.empty());
        when(courseRepository.findByName("BCC")).thenReturn(Optional.of(course));

        var input = new InputCreateStudent("Some name", "Some Ra", "BCC", 0, 0, Shift.MORNING.initial());
        createStudent.execute(input);

        verify(studentRepository).findByRa("Some Ra");
        verify(studentRepository).add(any());
    }
}
