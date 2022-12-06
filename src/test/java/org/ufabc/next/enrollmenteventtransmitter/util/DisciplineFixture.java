package org.ufabc.next.enrollmenteventtransmitter.util;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cp;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Discipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Professor;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.repository.DisciplineEntity;

import javax.transaction.Transactional;

public class DisciplineFixture {
    private DisciplineFixture() {

    }

    @Transactional
    public static IDiscipline aDiscipline(Course course,
                                          Professor practiceProfessor, Professor theoryProfessor) {
        var disciplineEntity = DisciplineEntity.toEntity(Discipline.aDiscipline()
                .withName("Some discipline")
                .withCode("Some code")
                .withCourse(course)
                .withCP(new Cp(1))
                .withCR(new Cr(3.4F))
                .withShift(Shift.MORNING)
                .withPracticeProfessor(practiceProfessor)
                .withTheoryProfessor(theoryProfessor)
                .withVacancies((short) 10)
                .build());
        disciplineEntity.persist();
        return DisciplineEntity.toModel(disciplineEntity);
    }

    @Transactional
    public static IDiscipline anotherDiscipline(Course course,
                                                Professor practiceProfessor, Professor theoryProfessor) {
        var disciplineEntity = DisciplineEntity.toEntity(Discipline.aDiscipline()
                .withName("anotherName")
                .withCode("anotherCode")
                .withCourse(course)
                .withCP(new Cp(0.5F))
                .withCR(new Cr(2.4F))
                .withShift(Shift.MORNING)
                .withPracticeProfessor(practiceProfessor)
                .withTheoryProfessor(theoryProfessor)
                .withVacancies((short) 50)
                .build());
        disciplineEntity.persist();
        return DisciplineEntity.toModel(disciplineEntity);
    }
}
