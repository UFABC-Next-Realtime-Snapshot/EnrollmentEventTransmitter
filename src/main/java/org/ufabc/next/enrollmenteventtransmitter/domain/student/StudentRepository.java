package org.ufabc.next.enrollmenteventtransmitter.domain.student;

import java.util.Optional;

public interface StudentRepository {

    void add(IStudent student);
    void update(IStudent student);
    Optional<IStudent> findByRa(String ra);
}
