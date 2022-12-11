package org.ufabc.next.enrollmenteventtransmitter.application.student.services;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cp;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@RequestScoped
public class CalculateCoefficientsOfDiscipline {
    @Inject
    EntityManager entityManager;

    public void execute(IDiscipline discipline) {
        if (!discipline.isFull()) {
            discipline.changeThresholdCr(new Cr(0));
            discipline.changeThresholdCp(new Cp(0));
            return;
        }

        String query = "SELECT cr, cp FROM (SELECT " +
                "(CASE course_id " +
                "WHEN :courseId THEN 1 " +
                "ELSE 0 END) AS withReservation, " +
                "(CASE shift " +
                "WHEN :disciplineShift THEN 1 " +
                "ELSE 0 END) as withSameShift, " +
                "cr, cp " +
                "FROM students " +
                "WHERE id IN (SELECT student_id FROM enrollments WHERE discipline_id = :disciplineId) " +
                "ORDER BY withReservation, withSameShift, cr, cp DESC " +
                "LIMIT :disciplineVacancies) ranking " +
                "ORDER BY withReservation, withSameShift, cr, cp ASC " +
                "LIMIT 1";
        var statement = entityManager.createNativeQuery(query);
        statement.setParameter("courseId", discipline.course().id());
        statement.setParameter("disciplineShift", discipline.shift().initial());
        statement.setParameter("disciplineId", discipline.id());
        statement.setParameter("disciplineVacancies", discipline.vacancies());

        var resultSet = statement.getResultList();
        if (resultSet.isEmpty()) {
            return;
        }

        var coefficients = (Object[]) resultSet.get(0);
        var cr = new Cr(Float.parseFloat(coefficients[0].toString()));
        var cp = new Cp(Float.parseFloat(coefficients[1].toString()));

        discipline.changeThresholdCr(cr);
        discipline.changeThresholdCp(cp);
    }
}
