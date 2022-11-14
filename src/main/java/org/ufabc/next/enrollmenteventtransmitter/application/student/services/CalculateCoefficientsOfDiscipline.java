package org.ufabc.next.enrollmenteventtransmitter.application.student.services;

import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cp;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;

@RequestScoped
public class CalculateCoefficientsOfDiscipline {
    @Inject
    EntityManager entityManager;

    public void execute(IDiscipline discipline){
        String query = "SELECT cr, cp FROM (SELECT \n"+
        "CASE course_id" + 
            "WHEN " + discipline.course().id() + "THEN 1" + 
            "ELSE 0 END as withReservation, \n" +
        "CASE shift" +
            "WHEN " + discipline.shift().initial() + "THEN 1" +
            "ELSE 0 END as withSameShift, \n" +
        "cr, cp \n" +
        "FROM students\n" +
        "WHERE id IN (SELECT student_id FROM enrollments WHERE discipline_id = " + discipline.id() + ")\n"+
        "ORDER BY withReservation, withSameShift, cr, cp DESC\n"+
        "LIMIT ?\n) ranking" +
        "LIMIT 1;"; 

        var coefficients = entityManager.createQuery(query, ArrayList.class).getSingleResult();
        var cr = new Cr((float) coefficients.get(0));
        var cp = new Cp((float) coefficients.get(1));
        discipline.changeThresholdCr(cr);
        discipline.changeThresholdCp(cp);
    }
}
