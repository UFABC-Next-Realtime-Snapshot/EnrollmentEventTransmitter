package org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.repository;


import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Professor;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "professors")
@ApplicationScoped
public class ProfessorEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;

    public static ProfessorEntity toEntity(Professor professor) {
        var professorEntity = new ProfessorEntity();
        professorEntity.name = professor.name();
        return professorEntity;
    }

    public static Professor toModel(ProfessorEntity professor) {
        return new Professor(professor.name);
    }
}
