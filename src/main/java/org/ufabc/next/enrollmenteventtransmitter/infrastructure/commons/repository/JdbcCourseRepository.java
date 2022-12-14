package org.ufabc.next.enrollmenteventtransmitter.infrastructure.commons.repository;

import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.CourseRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class JdbcCourseRepository implements CourseRepository {
    @Override
    public Optional<Course> findByName(String name) {
        if (name == null) {
            return Optional.empty();
        }

        var entity = CourseEntity
                .find("name = :name", Map.of("name", name))
                .singleResultOptional();

        if (entity.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(CourseEntity.toModel((CourseEntity) entity.get()));
    }
}
