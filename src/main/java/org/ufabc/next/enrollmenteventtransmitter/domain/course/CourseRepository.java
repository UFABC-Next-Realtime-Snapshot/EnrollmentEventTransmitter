package org.ufabc.next.enrollmenteventtransmitter.domain.course;

import java.util.Optional;

public interface CourseRepository {
    Optional<Course> findByName(String name);
}
