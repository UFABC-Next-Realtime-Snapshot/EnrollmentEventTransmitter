package org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.find;

public class DisciplineResponse {
    public String code;
    public String name;
    public String course;
    public String theoryProfessor;
    public String practiceProfessor;
    public short vacancies;
    public short subscribers;
    public char shift;
    public float thresholdCr;
    public float thresholdCp;

    public DisciplineResponse(String code, String name, String course,
                              String theoryProfessor, String practiceProfessor,
                              short vacancies, short subscribers, char shift, float thresholdCr, float thresholdCp) {
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
