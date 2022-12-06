package org.ufabc.next.enrollmenteventtransmitter.util;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.IStudent;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentBuilder;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.student.repository.StudentEntity;

import javax.transaction.Transactional;
import java.util.List;

public class StudentFixture {

    private StudentFixture() {

    }

    @Transactional
    public static IStudent aStudent(Course course, List<IDiscipline> disciplines) {
        var student = new StudentBuilder(
                null,
                "aName",
                "aRa",
                Shift.NIGHT
        )
                .withCp(1F)
                .withCr(4F)
                .withCourse(course)
                .withDisciplines(disciplines).build();
        var studentEntity = StudentEntity.toEntity(student);
        studentEntity.persist();
        return StudentEntity.toModel(studentEntity);
    }
}
