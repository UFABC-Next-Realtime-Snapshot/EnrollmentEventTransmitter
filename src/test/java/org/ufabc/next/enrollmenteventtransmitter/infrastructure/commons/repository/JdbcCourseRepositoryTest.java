package org.ufabc.next.enrollmenteventtransmitter.infrastructure.commons.repository;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.CourseRepository;
import org.ufabc.next.enrollmenteventtransmitter.util.Cleanable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class JdbcCourseRepositoryTest extends Cleanable {
    @Inject
    CourseRepository courseRepository;

    @Test
    public void shouldNotFindCourse() {
        var course = courseRepository.findByName("aCourse");

        assertFalse(course.isPresent());
    }

    @Test
    @Transactional
    public void shouldFindCourse() {
        String name = "aCourse";

        Course course = new Course(null, name);
        CourseEntity.toEntity(course).persist();

        var persistedCourse = courseRepository.findByName(name);

        assertTrue(persistedCourse.isPresent());
        assertEquals(course.name(), persistedCourse.get().name());
        assertEquals(1, persistedCourse.get().id());
    }
}
