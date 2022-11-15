package org.ufabc.next.enrollmenteventtransmitter.application.student.usecase;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.application.student.services.CalculateCoefficientsOfDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.update.InputUpdateStudent;
import org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.update.UpdateStudent;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.CourseRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Discipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.DisciplineRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Professor;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentBuilder;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
public class UpdateStudentTest {

        public StudentRepository studentRepository = mock(StudentRepository.class);
        public DisciplineRepository disciplineRepository = mock(DisciplineRepository.class);
        public CourseRepository courseRepository = mock(CourseRepository.class);
        public CalculateCoefficientsOfDiscipline calculateCoefficientsOfDiscipline = mock(
                        CalculateCoefficientsOfDiscipline.class);

        @Test
        public void whenStudentExistsShouldUpdateAndReturnOutputUpdateStudent() {
                var input = new InputUpdateStudent("Some RA", 1, 2.9F, "Other course", 'M');

                var discipline = Discipline.aDiscipline()
                                .withId(2L)
                                .withCode("Some Code")
                                .withCourse(new Course(1L, "Some Course"))
                                .withName("Some Discipline")
                                .withPracticeProfessor(new Professor("Some practice professor"))
                                .withTheoryProfessor(new Professor("Some theory professsor"))
                                .withVacancies((short) 10)
                                .withShift(Shift.MORNING)
                                .build();
                when(disciplineRepository.findByCode("Some Code")).thenReturn(Optional.of(discipline));
                when(courseRepository.findByName("Other course")).thenReturn(Optional.of(new Course(null, "Other course")));

                var student = new StudentBuilder(1L, "Some Name", "Some RA", Shift.MORNING)
                                .withDisciplines(List.of(discipline))
                                .build();
                when(studentRepository.findByRa("Some RA")).thenReturn(Optional.of(student));

                var usecase = new UpdateStudent(studentRepository, disciplineRepository, courseRepository,
                                calculateCoefficientsOfDiscipline);
                usecase.execute(input);

                verify(calculateCoefficientsOfDiscipline).execute(any());
                verify(studentRepository).update(any());
                verify(disciplineRepository).update(any());
                verify(courseRepository).findByName("Other course");
        }

        @Test
        public void whenStudentNotExistsShouldThrowsRuntimeException() {
                var input = new InputUpdateStudent("Some RA", 1, 2.9F, "Other course", 'M');

                when(courseRepository.findByName("Other course")).thenReturn(Optional.of(new Course(null, "Other course")));
                when(studentRepository.findByRa("Some RA")).thenReturn(Optional.empty());

                var usecase = new UpdateStudent(studentRepository, disciplineRepository, courseRepository,
                                calculateCoefficientsOfDiscipline);
                Exception exception = assertThrows(RuntimeException.class, () -> usecase.execute(input));

                assertEquals("student Some RA not exists", exception.getMessage());
        }

        @Test
        public void whenCourseNotExistsShouldThrowsRuntimeException() {
                var input = new InputUpdateStudent("Some RA", 1, 2.9F, "Other course", 'M');

                var discipline = Discipline.aDiscipline()
                                .withId(2L)
                                .withCode("Some Code")
                                .withCourse(new Course(1L, "Some Course"))
                                .withName("Some Discipline")
                                .withPracticeProfessor(new Professor("Some practice professor"))
                                .withTheoryProfessor(new Professor("Some theory professsor"))
                                .withVacancies((short) 10)
                                .withShift(Shift.MORNING)
                                .build();
                when(disciplineRepository.findByCode("Some Code")).thenReturn(Optional.of(discipline));
                when(courseRepository.findByName("Other course")).thenReturn(Optional.empty());

                var student = new StudentBuilder(1L, "Some Name", "Some RA", Shift.MORNING)
                                .withDisciplines(List.of(discipline))
                                .build();
                when(studentRepository.findByRa("Some RA")).thenReturn(Optional.of(student));

                var usecase = new UpdateStudent(studentRepository, disciplineRepository, courseRepository,
                                calculateCoefficientsOfDiscipline);
                Exception exception = assertThrows(RuntimeException.class, () -> usecase.execute(input));

                assertEquals("course Other course not exists", exception.getMessage());
        }

        @Test
        public void whenCrIsInvalidShouldThrowsRuntimeException() {
                var input = new InputUpdateStudent("Some RA", 1, 5, "Other course", 'M');

                var discipline = Discipline.aDiscipline()
                                .withId(2L)
                                .withCode("Some Code")
                                .withCourse(new Course(1L, "Some Course"))
                                .withName("Some Discipline")
                                .withPracticeProfessor(new Professor("Some practice professor"))
                                .withTheoryProfessor(new Professor("Some theory professsor"))
                                .withVacancies((short) 10)
                                .withShift(Shift.MORNING)
                                .build();
                when(disciplineRepository.findByCode("Some Code")).thenReturn(Optional.of(discipline));
                when(courseRepository.findByName("Other course")).thenReturn(Optional.of(new Course(null, "Other course")));


                var student = new StudentBuilder(1L, "Some Name", "Some RA", Shift.MORNING)
                                .withDisciplines(List.of(discipline))
                                .build();
                when(studentRepository.findByRa("Some RA")).thenReturn(Optional.of(student));

                var usecase = new UpdateStudent(studentRepository, disciplineRepository, courseRepository,
                                calculateCoefficientsOfDiscipline);
                Exception exception = assertThrows(RuntimeException.class, () -> usecase.execute(input));

                assertEquals("CR: value greater than 4", exception.getMessage());
        }

        @Test
        public void whenCpIsInvalidShouldThrowsRuntimeException() {
                var input = new InputUpdateStudent("Some RA", 2, 2.9F, "Other course", 'M');

                var discipline = Discipline.aDiscipline()
                                .withId(2L)
                                .withCode("Some Code")
                                .withCourse(new Course(1L, "Some Course"))
                                .withName("Some Discipline")
                                .withPracticeProfessor(new Professor("Some practice professor"))
                                .withTheoryProfessor(new Professor("Some theory professsor"))
                                .withVacancies((short) 10)
                                .withShift(Shift.MORNING)
                                .build();
                when(disciplineRepository.findByCode("Some Code")).thenReturn(Optional.of(discipline));
                when(courseRepository.findByName("Other course")).thenReturn(Optional.of(new Course(null, "Other course")));

                var student = new StudentBuilder(1L, "Some Name", "Some RA", Shift.MORNING)
                                .withDisciplines(List.of(discipline))
                                .build();
                when(studentRepository.findByRa("Some RA")).thenReturn(Optional.of(student));

                var usecase = new UpdateStudent(studentRepository, disciplineRepository, courseRepository,
                                calculateCoefficientsOfDiscipline);
                Exception exception = assertThrows(RuntimeException.class, () -> usecase.execute(input));
                assertEquals("cp: invalid value, must be lower 1", exception.getMessage());

        }
}
