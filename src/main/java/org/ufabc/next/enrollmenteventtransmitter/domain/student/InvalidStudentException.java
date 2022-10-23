package org.ufabc.next.enrollmenteventtransmitter.domain.student;

public class InvalidStudentException extends Exception{
    public InvalidStudentException(String message){
        super(message);
    }
}
