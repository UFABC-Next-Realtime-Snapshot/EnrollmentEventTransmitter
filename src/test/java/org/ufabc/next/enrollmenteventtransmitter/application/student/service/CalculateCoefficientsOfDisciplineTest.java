package org.ufabc.next.enrollmenteventtransmitter.application.student.service;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.util.Cleanable;
import org.ufabc.next.enrollmenteventtransmitter.application.student.services.CalculateCoefficientsOfDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cp;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Discipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Professor;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class CalculateCoefficientsOfDisciplineTest extends Cleanable {
    private final Long courseId = 12456566L;
    private final String courseName = "Test";
    private final Long otherCourseId = 12456598L;
    private final String otherCourseName = "Test";
    private final Long disciplineId = 12456566L;
    private final String disciplineName = "Test";
    private final String disciplineCode = "Some";
    private final Shift disciplineShift = Shift.MORNING;
    private final Shift otherDisciplineShift = Shift.NIGHT;
    private final Long junkStudentId = 888L;
    private final Long studentIdWithSameDisciplineCourseAndShift = 1L;
    private final Long studentIdWithSameDisciplineCourse = 2L;
    private final Long studentIdWithSameDisciplineShift = 3L;
    private final Long studentId = 4L;
    @Inject
    EntityManager entityManager;
    @Inject
    CalculateCoefficientsOfDiscipline service;
    private Course course;
    private Course otherCourse;
    private IDiscipline discipline;

    @BeforeEach
    @Transactional
    public void setup() {
        entityManager.createNativeQuery("INSERT INTO professors (id, name) VALUES (:id, :name)")
                .setParameter("id", 1L)
                .setParameter("name", "Some")
                .executeUpdate();

        entityManager.createNativeQuery("INSERT INTO professors (id, name) VALUES (:id, :name)")
                .setParameter("id", 2L)
                .setParameter("name", "Other")
                .executeUpdate();

        entityManager.createNativeQuery("INSERT INTO courses (id, name) VALUES (:id, :name)")
                .setParameter("id", courseId)
                .setParameter("name", courseName)
                .executeUpdate();
        course = new Course(courseId, courseName);

        entityManager.createNativeQuery("INSERT INTO courses (id, name) VALUES (:id, :name)")
                .setParameter("id", otherCourseId)
                .setParameter("name", otherCourse)
                .executeUpdate();
        otherCourse = new Course(otherCourseId, otherCourseName);

        entityManager.createNativeQuery("INSERT INTO " +
                        "disciplines (id, name, code, course_id, shift, cr, cp, theory_professor_id, practice_professor_id, vacancies, subscribers) " +
                        "VALUES (:id, :name, :code, :course_id, :shift, :cr, " +
                        ":cp, :theory_professor_id, :practice_professor_id, :vacancies, :subscribers)")
                .setParameter("id", disciplineId)
                .setParameter("name", disciplineName)
                .setParameter("code", disciplineCode)
                .setParameter("course_id", courseId)
                .setParameter("shift", disciplineShift.initial())
                .setParameter("cr", 0F)
                .setParameter("cp", 0F)
                .setParameter("vacancies", 3)
                .setParameter("subscribers", 3)
                .setParameter("theory_professor_id", 1L)
                .setParameter("practice_professor_id", 2L)
                .executeUpdate();
        discipline = Discipline.aDiscipline()
                .withId(disciplineId)
                .withName(disciplineName)
                .withCode(disciplineCode)
                .withCourse(course)
                .withShift(disciplineShift)
                .withCR(new Cr(0))
                .withCP(new Cp(0))
                .withVacancies((short) 3)
                .withSubscribers((short) 3)
                .withTheoryProfessor(new Professor("Some"))
                .withPracticeProfessor(new Professor("Other"))
                .build();

        entityManager.createNativeQuery("INSERT INTO students (id, ra, name, shift, cr, cp, course_id)" +
                        " VALUES (:id, :ra, :name, :shift, :cr, :cp, :course_id)")
                .setParameter("id", studentIdWithSameDisciplineCourseAndShift)
                .setParameter("ra", 11278732L)
                .setParameter("name", "Bom De Guerra")
                .setParameter("shift", disciplineShift.initial())
                .setParameter("cr", 2.9F)
                .setParameter("cp", 1)
                .setParameter("course_id", courseId)
                .executeUpdate();

        entityManager.createNativeQuery("INSERT INTO students (id, ra, name, shift, cr, cp, course_id)" +
                        " VALUES (:id, :ra, :name, :shift, :cr, :cp, :course_id)")
                .setParameter("id", studentIdWithSameDisciplineShift)
                .setParameter("ra", 11111111L)
                .setParameter("name", "Friendly Test")
                .setParameter("shift", disciplineShift.initial())
                .setParameter("cr", 1.0F)
                .setParameter("cp", 0.8F)
                .setParameter("course_id", otherCourseId)
                .executeUpdate();

        entityManager.createNativeQuery("INSERT INTO students (id, ra, name, shift, cr, cp, course_id)" +
                        " VALUES (:id, :ra, :name, :shift, :cr, :cp, :course_id)")
                .setParameter("id", studentIdWithSameDisciplineCourse)
                .setParameter("ra", 1234967L)
                .setParameter("name", "C07n0 J0B")
                .setParameter("shift", otherDisciplineShift.initial())
                .setParameter("cr", 1.2F)
                .setParameter("cp", 0.1F)
                .setParameter("course_id", courseId)
                .executeUpdate();

        entityManager.createNativeQuery("INSERT INTO students (id, ra, name, shift, cr, cp, course_id)" +
                        " VALUES (:id, :ra, :name, :shift, :cr, :cp, :course_id)")
                .setParameter("id", studentId)
                .setParameter("ra", "48439434")
                .setParameter("name", "George")
                .setParameter("shift", otherDisciplineShift.initial())
                .setParameter("cr", 1.3F)
                .setParameter("cp", 1F)
                .setParameter("course_id", otherCourseId)
                .executeUpdate();
    }

    @Test
    @Transactional
    public void shouldNotChangeCoefficientsOfDisciplineWhenAddDiscipline() {
        service.execute(discipline);
        assertEquals(0.0F, discipline.thresholdCr().value());
        assertEquals(0.0F, discipline.thresholdCp().value());
    }

    @Test
    @Transactional
    public void shouldNotChangeCoefficientsOfDisciplineWhenAddStudent() {
        entityManager.createNativeQuery("INSERT INTO students (id, ra, name, shift, cr, cp, course_id)" +
                " VALUES (:id, :ra, :name, :shift, :cr, :cp, :course_id)")
                .setParameter("id", junkStudentId)
                .setParameter("ra", "11278732325532")
                .setParameter("name", "Bom De Guerra")
                .setParameter("shift", disciplineShift.initial())
                .setParameter("cr", 2.9F)
                .setParameter("cp", 1F)
                .setParameter("course_id", courseId)
                .executeUpdate();
        service.execute(discipline);
        assertEquals(0.0F, discipline.thresholdCr().value());
        assertEquals(0.0F, discipline.thresholdCp().value());
    }

    @Test
    @Transactional
    public void shouldChangeCoefficientsOfDisciplineWhenEnrollStudentAsc() {
        entityManager.createNativeQuery("INSERT INTO enrollments (discipline_id, student_id) " +
                "VALUES (:discipline_id, :student_id)")
                .setParameter("discipline_id", disciplineId)
                .setParameter("student_id", studentId)
                .executeUpdate();
        service.execute(discipline);
        assertEquals(1.3F, discipline.thresholdCr().value());
        assertEquals(1F, discipline.thresholdCp().value());
        entityManager.createNativeQuery("INSERT INTO enrollments (discipline_id, student_id) " +
                        "VALUES (:discipline_id, :student_id)")
                .setParameter("discipline_id", disciplineId)
                .setParameter("student_id", studentIdWithSameDisciplineCourse)
                .executeUpdate();
        assertEquals(1.3F, discipline.thresholdCr().value());
        assertEquals(1F, discipline.thresholdCp().value());
        entityManager.createNativeQuery("INSERT INTO enrollments (discipline_id, student_id) " +
                        "VALUES (:discipline_id, :student_id)")
                .setParameter("discipline_id", disciplineId)
                .setParameter("student_id", studentIdWithSameDisciplineCourseAndShift)
                .executeUpdate();
        assertEquals(1.3F, discipline.thresholdCr().value());
        assertEquals(1F, discipline.thresholdCp().value());
    }

    @Test
    @Transactional
    public void shouldChangeCoefficientsOfDisciplineWhenEnrollStudentDesc() {
        entityManager.createNativeQuery("INSERT INTO enrollments (discipline_id, student_id) " +
                        "VALUES (:discipline_id, :student_id)")
                .setParameter("discipline_id", disciplineId)
                .setParameter("student_id", studentIdWithSameDisciplineCourseAndShift)
                .executeUpdate();
        service.execute(discipline);
        assertEquals(2.9F, discipline.thresholdCr().value());
        assertEquals(1F, discipline.thresholdCp().value());
        entityManager.createNativeQuery("INSERT INTO enrollments (discipline_id, student_id) " +
                        "VALUES (:discipline_id, :student_id)")
                .setParameter("discipline_id", disciplineId)
                .setParameter("student_id", studentIdWithSameDisciplineCourse)
                .executeUpdate();
        service.execute(discipline);
        assertEquals(1.2F, discipline.thresholdCr().value());
        assertEquals(0.1F, discipline.thresholdCp().value());
        entityManager.createNativeQuery("INSERT INTO enrollments (discipline_id, student_id) " +
                        "VALUES (:discipline_id, :student_id)")
                .setParameter("discipline_id", disciplineId)
                .setParameter("student_id", studentIdWithSameDisciplineShift)
                .executeUpdate();
        service.execute(discipline);
        assertEquals(1.0F, discipline.thresholdCr().value());
        assertEquals(0.8F, discipline.thresholdCp().value());
        entityManager.createNativeQuery("INSERT INTO enrollments (discipline_id, student_id) " +
                        "VALUES (:discipline_id, :student_id)")
                .setParameter("discipline_id", disciplineId)
                .setParameter("student_id", studentId)
                .executeUpdate();
        service.execute(discipline);
        assertEquals(1.3F, discipline.thresholdCr().value());
        assertEquals(1F, discipline.thresholdCp().value());
    }
}
