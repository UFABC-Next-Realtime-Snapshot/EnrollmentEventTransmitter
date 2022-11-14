package org.ufabc.next.enrollmenteventtransmitter.domain.course;

public class Course {
    private Long id;
    private String name;

    public Course(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Long id(){
        return this.id;
    }

    public String name(){
        return this.name;
    }
}
