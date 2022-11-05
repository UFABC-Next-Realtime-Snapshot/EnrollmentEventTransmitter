package org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.enroll;

import java.util.List;

public class InputEnrollStudent {
    private final String studentRa;
    private final List<String> disciplineCodes;

    public InputEnrollStudent(String studentRa, List<String> disciplineCodes){
        this.studentRa = studentRa;
        this.disciplineCodes = disciplineCodes;
    }

    public String studentRa(){
        return this.studentRa;
    }

    public List<String> disciplineCodes(){
        return this.disciplineCodes;
    }
}
