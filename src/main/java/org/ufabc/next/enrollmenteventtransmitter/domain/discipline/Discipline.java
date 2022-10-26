package org.ufabc.next.enrollmenteventtransmitter.domain.discipline;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cp;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Cr;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Shift;

import static org.ufabc.next.enrollmenteventtransmitter.domain.commons.PreConditions.checkNotNull;

public class Discipline implements IDiscipline {
    private String code;
    private String name;
    private Course course;
    private Professor theoryProfessor;
    private Professor practiceProfessor;
    private short vacancies;
    private Shift shift;
    private Cr thresholdCr;
    private Cp thresholdCp;

    private Discipline() {
    }

    public static Builder aDiscipline() {
        return new Builder();
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
    public Course course() {
        return this.course;
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

    public static final class Builder {

        private String code;
        private String name;
        private Course course;
        private Professor theoryProfessor;
        private Professor practiceProfessor;
        private short vacancies;
        private Shift shift;
        private Cr thresholdCr;
        private Cp thresholdCp;

        Builder() {
        }

        public Builder withCode(String code) {
            this.code = code;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCourse(Course course) {
            this.course = course;
            return this;
        }

        public Builder withTheoryProfessor(Professor theoryProfessor) {
            this.theoryProfessor = theoryProfessor;
            return this;
        }

        public Builder withPracticeProfessor(Professor practiceProfessor) {
            this.practiceProfessor = practiceProfessor;
            return this;
        }

        public Builder withVacancies(short vacancies) {
            this.vacancies = vacancies;
            return this;
        }

        public Builder withShift(Shift shift) {
            this.shift = shift;
            return this;
        }

        public Builder withCR(Cr thresholdCr) {
            this.thresholdCr = thresholdCr;
            return this;
        }

        public Builder withCP(Cp thresholdCp) {
            this.thresholdCp = thresholdCp;
            return this;
        }

        public Discipline build() {
            Discipline discipline = new Discipline();
            discipline.code = checkNotNull(this.code, "code");
            discipline.name = checkNotNull(this.name, "name");
            discipline.course = checkNotNull(this.course, "course");
            discipline.theoryProfessor = this.theoryProfessor;
            discipline.practiceProfessor = this.practiceProfessor;
            discipline.vacancies = this.vacancies;
            discipline.shift = checkNotNull(this.shift, "shift");
            discipline.thresholdCr = thresholdCr != null ? thresholdCr : new Cr();
            discipline.thresholdCp = thresholdCp != null ? thresholdCp : new Cp();

            return discipline;
        }
    }
}