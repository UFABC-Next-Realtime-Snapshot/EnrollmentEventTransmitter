package org.ufabc.next.enrollmenteventtransmitter.infrastructure.student.repository;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.IStudent;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentBuilder;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.commons.repository.CourseEntity;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.repository.DisciplineEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class StudentEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    public String ra;
    public float cr;
    public float cp;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    public CourseEntity course;
    public char shift;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "enrollments",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "discipline_id")})
    public List<DisciplineEntity> disciplines;

    public static StudentEntity toEntity(IStudent student) {
        var studentEntity = new StudentEntity();
        studentEntity.ra = student.ra().value();
        studentEntity.name = student.name();
        studentEntity.cr = student.cr().value();
        studentEntity.cp = student.cp().value();
        studentEntity.course = CourseEntity.toEntity(student.course());
        studentEntity.disciplines = student.disciplines().stream()
                .map(DisciplineEntity::toEntity).toList();
        studentEntity.shift = student.shift().initial();
        return studentEntity;
    }

    public static IStudent toModel(StudentEntity student) {
        return new StudentBuilder(student.id, student.name, student.ra, Shift.fromInitial(student.shift))
                .withCp(student.cp)
                .withCr(student.cr)
                .withCourse(CourseEntity.toModel(student.course))
                .withDisciplines(student.disciplines.stream()
                        .map(DisciplineEntity::toModel)
                        .toList())
                .build();
    }

    public void setCr(float cr) {
        this.cr = cr;
    }

    public void setCp(float cp) {
        this.cp = cp;
    }

    public void setCourse(Course course) {
        this.course = CourseEntity.toEntity(course);
    }

    public void setShift(Shift shift) {
        this.shift = shift.initial();
    }

    public void cleanableAddAll(List<IDiscipline> disciplines) {
        this.disciplines = new ArrayList<>(disciplines.stream()
                .map(DisciplineEntity::toEntity)
                .toList());
    }
}
