package org.ufabc.next.enrollmenteventtransmitter.util;

import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Professor;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.repository.ProfessorEntity;

import javax.transaction.Transactional;

public class ProfessorFixture {

    private ProfessorFixture() {
    }

    @Transactional
    public static Professor practiceProfessor() {
        var professorEntity = ProfessorEntity.toEntity(new Professor("aPracticeProfessorName"));
        professorEntity.persist();
        return ProfessorEntity.toModel(professorEntity);
    }

    @Transactional
    public static Professor theoryProfessor() {
        var professorEntity = ProfessorEntity.toEntity(new Professor("aTheoryProfessorName"));
        professorEntity.persist();
        return ProfessorEntity.toModel(professorEntity);
    }
}
