package org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc;

import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Professor;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.ProfessorRepository;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc.framework.JdbcTemplate;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc.framework.RowMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class JdbcProfessorRepository implements ProfessorRepository {

    @Inject
    JdbcTemplate jdbcTemplate;

    @Override
    public void add(Professor professor) {
        jdbcTemplate.update("INSERT INTO professors (name) VALUES (?)",
                (statement) -> statement.setString(1, professor.name()));
    }

    public static class ProfessorRowMapper implements RowMapper<Professor> {

        @Override
        public Professor mapRow(ResultSet resultSet) throws SQLException {
            return new Professor(resultSet.getString("name"));
        }
    }
}
