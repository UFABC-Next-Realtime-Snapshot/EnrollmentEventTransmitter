package org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.rest.request;

public class DisciplineRequest {
    public String name;
    public String course;
    public String practicalProfessor;
    public String theoryProfessor;
    public String code;
    public Character shift;
    public short vacancies;

    public DisciplineRequest(String name, String code,
                             String practicalProfessor, String theoryProfessor,
                              String course, Character shift, short vacancies) {
        this.course = course;
        this.practicalProfessor = practicalProfessor;
        this.theoryProfessor = theoryProfessor;
        this.name = name;
        this.code = code;
        this.shift = shift;
        this.vacancies = vacancies;
    }

    public DisciplineRequest() {
    }

    public String name() {
        return name;
    }

    public String course() {
        return course;
    }

    public String practicalProfessor() {
        return practicalProfessor;
    }

    public String theoryProfessor() {
        return theoryProfessor;
    }

    public String code() {
        return code;
    }

    public Character shift() {
        return shift;
    }

    public short vacancies() {
        return vacancies;
    }
}
