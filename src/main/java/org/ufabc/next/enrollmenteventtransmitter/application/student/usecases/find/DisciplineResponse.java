package org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.find;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cp;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;

public class DisciplineResponse {
    public String code;
    public String name;
    public String course;
    public String theoryProfessor;
    public String practiceProfessor;
    public short vacancies;
    public short subscribers;
    public Shift shift;
    public Cr thresholdCr;
    public Cp thresholdCp;

    public DisciplineResponse(String code, String name, String course,
                              String theoryProfessor, String practiceProfessor,
                              short vacancies, short subscribers, Shift shift, Cr thresholdCr, Cp thresholdCp) {
        this.code = code;
        this.name = name;
        this.course = course;
        this.theoryProfessor = theoryProfessor;
        this.practiceProfessor = practiceProfessor;
        this.vacancies = vacancies;
        this.subscribers = subscribers;
        this.shift = shift;
        this.thresholdCr = thresholdCr;
        this.thresholdCp = thresholdCp;
    }

    public DisciplineResponse() {

    }
}
