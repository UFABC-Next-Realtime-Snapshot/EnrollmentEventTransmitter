package org.ufabc.next.enrollmenteventtransmitter.domain.discipline;

import java.util.List;

public interface DisciplineRepository {
    void add(IDiscipline discipline);
    void update(IDiscipline discipline);
    IDiscipline findByCode(String code);
    List<IDiscipline> findAll();
}
