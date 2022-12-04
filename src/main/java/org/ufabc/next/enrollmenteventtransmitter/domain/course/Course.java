package org.ufabc.next.enrollmenteventtransmitter.domain.course;

public class Course {
    private final Long id;
    private final String name;

    public Course(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Course(String name){
        this.id = null;
        this.name = name;
    }

    public Long id(){
        return this.id;
    }

    public String name(){
        return this.name;
    }
}
