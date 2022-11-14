package org.ufabc.next.enrollmenteventtransmitter.infrastructure.commons.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "courses")
public class CourseEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;

    public static CourseEntity toEntity(Course course) {
        var courseEntity = new CourseEntity();
        courseEntity.name = course.name();
        return courseEntity;
    }

    public static Course toModel(CourseEntity course) {
        return new Course(course.id, course.name);
    }
}
