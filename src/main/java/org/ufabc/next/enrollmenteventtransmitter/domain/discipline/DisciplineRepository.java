package org.ufabc.next.enrollmenteventtransmitter.domain.discipline;

import java.util.List;
import java.util.Optional;

public interface DisciplineRepository {
    void add(IDiscipline discipline);
    void update(IDiscipline discipline);
    Optional<IDiscipline> findByCode(String code);
    List<IDiscipline> findAll();
}
