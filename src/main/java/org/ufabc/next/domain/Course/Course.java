package org.ufabc.next.domain.Course;

import org.ufabc.next.domain.commons.valueObjects.Cp;
import org.ufabc.next.domain.commons.valueObjects.Cr;
import org.ufabc.next.domain.commons.valueObjects.Shift;

public class Course implements ICourse {
    private final String code;
    private final String name;
    private Professor theoryProfessor;
    private Professor practiceProfessor;
    private final short vacancies;
    private final Shift shift;
    private Cr thresholdCr;
    private Cp thresholdCp;

    // private Student[] studentRanking;

    public Course(
        String code,
        String name,
        Professor tProfessor,
        Professor pProfessor,
        short vacancies,
        Shift shift,
        Cr thresholdCr,
        Cp thresholdCp) {
        this.code = code;
        this.name = name;
        this.theoryProfessor = tProfessor;
        this.practiceProfessor = pProfessor;
        this.vacancies = vacancies;
        this.shift = shift;
        this.thresholdCr = thresholdCr != null ? thresholdCr : new Cr();
        this.thresholdCp = thresholdCp != null ? thresholdCp : new Cp();
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Professor theoryProfessor() {
        return this.theoryProfessor;
    }

    @Override
    public Professor practiceProfessor() {
        return this.practiceProfessor;
    }

    @Override
    public short vacancies() {
        return this.vacancies;
    }

    @Override
    public Shift shift() {
        return this.shift;
    }

    @Override
    public Cr thresholdCr() {
        return this.thresholdCr;
    }

    @Override
    public Cp thresholdCp() {
        return this.thresholdCp;
    }
}
