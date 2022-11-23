package org.ufabc.next.enrollmenteventtransmitter.util;

import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.commons.repository.CourseEntity;

public class CourseFixture {

    private CourseFixture() {

    }

    public static Course aCourse() {
        var courseEntity = CourseEntity.toEntity(new Course(null, "aCourse"));
        courseEntity.persist();
        return CourseEntity.toModel(courseEntity);
    }

    public static Course anotherCourse() {
        var courseEntity = CourseEntity.toEntity(new Course(null, "anotherCourse"));
        courseEntity.persist();
        return CourseEntity.toModel(courseEntity);
    }
}
