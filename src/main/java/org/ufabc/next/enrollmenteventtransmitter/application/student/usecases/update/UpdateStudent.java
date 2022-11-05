package org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.update;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class UpdateStudent {
    
    public OutputUpdateStudent execute(InputUpdateStudent input){
        return new OutputUpdateStudent();
    }
}
