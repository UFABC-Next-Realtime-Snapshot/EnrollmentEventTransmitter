package org.ufabc.next.enrollmenteventtransmitter.infrastructure.student.repository;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.IStudent;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentBuilder;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.commons.repository.CourseEntity;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.repository.DisciplineEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "students")
class StudentEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    public String ra;
    public float cr;
    public float cp;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="course_id")
    public CourseEntity course;
    public char shift;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "enrollment",
            joinColumns = {@JoinColumn(name = "fk_enrollment_discipline")},
            inverseJoinColumns = {@JoinColumn(name = "fk_enrollment_student")})
    public List<DisciplineEntity> disciplines;

    public static StudentEntity toEntity(IStudent student) {
        var studentEntity = new StudentEntity();
        studentEntity.ra = student.ra().value();
        studentEntity.name = student.name();
        studentEntity.cr = student.cr().value();
        studentEntity.cp = student.cp().value();
        studentEntity.course = CourseEntity.toEntity(student.course());
        studentEntity.shift = student.shift().initial();
        return studentEntity;
    }

    public static IStudent toModel(StudentEntity student) {
        return new StudentBuilder(student.id, student.ra, student.name, Shift.fromInitial(student.shift))
                .withCp(student.cp)
                .withCr(student.cr)
                .withCourse(null)
                .withDisciplines(null)
                .build();
    }
}
