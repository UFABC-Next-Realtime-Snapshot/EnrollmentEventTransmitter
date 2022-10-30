package org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.IStudent;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentBuilder;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc.framework.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<IStudent> {

    @Override
    public IStudent mapRow(ResultSet resultSet) throws SQLException {
        return new StudentBuilder(
                resultSet.getString("name"),
                resultSet.getString("ra"),
                Shift.fromInitial(resultSet.getString("shift").charAt(0))
        )
                .withCr(resultSet.getFloat("cr"))
                .withCp(resultSet.getFloat("cp"))
                .withCourse(Course.valueOf(resultSet.getString("course_name")))
                .build();
    }
}
