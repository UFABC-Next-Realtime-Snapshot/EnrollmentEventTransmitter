package org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.repository;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cp;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Discipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.commons.repository.CourseEntity;

import javax.persistence.*;

@Entity
@Table(name = "disciplines")
public class DisciplineEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String code;
    public String name;
    public float cr;
    public float cp;
    public char shift;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    public CourseEntity course;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "theory_professor_id")
    public ProfessorEntity theoryProfessor;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "practice_professor_id")
    public ProfessorEntity practiceProfessor;
    public short vacancies;
    public short subscribers;

    public void setCr(float cr) {
        this.cr = cr;
    }

    public void setCp(float cp) {
        this.cp = cp;
    }

    public void setSubscribers(short subscribers) {
        this.subscribers = subscribers;
    }

    public static DisciplineEntity toEntity(IDiscipline discipline) {
        var disciplineEntity = new DisciplineEntity();
        disciplineEntity.id = discipline.id();
        disciplineEntity.code = discipline.code();
        disciplineEntity.name = discipline.name();
        disciplineEntity.cr = discipline.thresholdCr().value();
        disciplineEntity.cp = discipline.thresholdCp().value();
        disciplineEntity.shift = discipline.shift().initial();
        disciplineEntity.course = CourseEntity.toEntity(discipline.course());
        disciplineEntity.theoryProfessor = discipline.theoryProfessor() == null ?
                null : ProfessorEntity.toEntity(discipline.theoryProfessor());
        disciplineEntity.practiceProfessor = discipline.practiceProfessor() == null ?
                null : ProfessorEntity.toEntity(discipline.practiceProfessor());
        disciplineEntity.vacancies = discipline.vacancies();
        disciplineEntity.subscribers = discipline.subscribers();
        return disciplineEntity;
    }

    public static IDiscipline toModel(DisciplineEntity discipline) {
        return Discipline.aDiscipline()
                .withId(discipline.id)
                .withCode(discipline.code)
                .withName(discipline.name)
                .withCR(new Cr(discipline.cr))
                .withCP(new Cp(discipline.cp))
                .withShift(Shift.fromInitial(discipline.shift))
                .withCourse(CourseEntity.toModel(discipline.course))
                .withTheoryProfessor(discipline.theoryProfessor == null ?
                        null : ProfessorEntity.toModel(discipline.theoryProfessor))
                .withPracticeProfessor(discipline.practiceProfessor == null?
                        null: ProfessorEntity.toModel(discipline.practiceProfessor))
                .withVacancies(discipline.vacancies)
                .withSubscribers(discipline.subscribers)
                .build();
    }
}
