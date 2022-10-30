package org.ufabc.next.enrollmenteventtransmitter.domain.student;

public class InvalidStudentException extends RuntimeException {
    public InvalidStudentException(String message){
        super(message);
    }
}
