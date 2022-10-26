package org.ufabc.next.enrollmenteventtransmitter.domain.discipline;

import org.ufabc.next.enrollmenteventtransmitter.domain.student.Student;

import java.util.List;

public interface DisciplineRepository {
    void addDiscipline(Discipline discipline);
    Discipline updateDiscipline(Discipline discipline);
    Discipline findDisciplineByCode(String code);
    List<Discipline> findAllDisciplines();
    List<Discipline> findDisciplinesByStudent(Student student);
}
