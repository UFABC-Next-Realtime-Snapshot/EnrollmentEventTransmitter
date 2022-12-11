package org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.websocket;

import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;

public class DisciplineResponse {
    public String code;
    public String name;
    public String course;
    public String theoryProfessor;
    public String practiceProfessor;
    public short vacancies;
    public short subscribers;
    public char shift;
    public Float thresholdCr;
    public Float thresholdCp;

    public DisciplineResponse() {
    }

    private DisciplineResponse(String code, String name, String course,
                               String theoryProfessor, String practiceProfessor,
                               short vacancies, short subscribers, char shift,
                               Float thresholdCr, Float thresholdCp) {
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

    public static DisciplineResponse from(IDiscipline discipline) {
        return new DisciplineResponse(discipline.code(), discipline.name(), discipline.course().name(),
                discipline.theoryProfessor() == null ? null : discipline.theoryProfessor().name(),
                discipline.practiceProfessor() == null ? null : discipline.practiceProfessor().name(),
                discipline.vacancies(), discipline.subscribers(), discipline.shift().initial(),
                discipline.thresholdCr().value(), discipline.thresholdCp().value());
    }
}
