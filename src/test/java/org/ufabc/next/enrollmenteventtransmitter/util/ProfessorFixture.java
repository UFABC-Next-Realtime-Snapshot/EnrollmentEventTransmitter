package org.ufabc.next.enrollmenteventtransmitter.util;

import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Professor;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.repository.ProfessorEntity;

public class ProfessorFixture {

    private ProfessorFixture() {
    }

    public static Professor practiceProfessor() {
        var professorEntity = ProfessorEntity.toEntity(new Professor("aPracticeProfessorName"));
        professorEntity.persist();
        return ProfessorEntity.toModel(professorEntity);
    }

    public static Professor theoryProfessor() {
        var professorEntity = ProfessorEntity.toEntity(new Professor("aTheoryProfessorName"));
        professorEntity.persist();
        return ProfessorEntity.toModel(professorEntity);
    }
}
