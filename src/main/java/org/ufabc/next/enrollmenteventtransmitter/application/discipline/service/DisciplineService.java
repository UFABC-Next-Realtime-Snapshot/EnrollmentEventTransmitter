package org.ufabc.next.enrollmenteventtransmitter.application.discipline.service;

import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.DisciplineRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DisciplineService {

    private final DisciplineRepository disciplineRepository;

    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    @Transactional
    public Optional<IDiscipline> findByCode(String code) {
        return disciplineRepository.findByCode(code);
    }

    @Transactional
    public List<IDiscipline> findAll() {
        return disciplineRepository.findAll();
    }
}
