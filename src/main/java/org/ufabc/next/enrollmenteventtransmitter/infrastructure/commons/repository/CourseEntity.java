package org.ufabc.next.enrollmenteventtransmitter.infrastructure.commons.repository;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;

import javax.persistence.*;

@Entity
@Table(name = "courses")
public class CourseEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;

    public static CourseEntity toEntity(Course course) {
        var courseEntity = new CourseEntity();
        courseEntity.id = course.id();
        courseEntity.name = course.name();
        return courseEntity;
    }

    public static Course toModel(CourseEntity course) {
        return new Course(course.id, course.name);
    }
}
