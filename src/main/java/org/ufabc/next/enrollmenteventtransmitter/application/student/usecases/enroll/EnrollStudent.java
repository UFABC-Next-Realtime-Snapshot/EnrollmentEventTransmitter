package org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.enroll;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import org.ufabc.next.enrollmenteventtransmitter.application.commons.events.IEventDispatcher;
import org.ufabc.next.enrollmenteventtransmitter.application.student.events.StudentRegisteredInDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.application.student.events.StudentRemovedFromDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.application.student.services.CalculateCoefficientsOfDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.DisciplineRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentBuilder;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;

@RequestScoped
public class EnrollStudent {

    private StudentRepository studentRepository;
    private DisciplineRepository disciplineRepository;
    private CalculateCoefficientsOfDiscipline calculateCoefficientsOfDiscipline;
    private IEventDispatcher dispatcher;

    public EnrollStudent(StudentRepository studentRepository, DisciplineRepository disciplineRepository, CalculateCoefficientsOfDiscipline calculateCoefficientsOfDiscipline, IEventDispatcher dispatcher) {
        this.studentRepository = studentRepository;
        this.disciplineRepository = disciplineRepository;
        this.calculateCoefficientsOfDiscipline = calculateCoefficientsOfDiscipline;
        this.dispatcher = dispatcher;
    }

    public OutputEnrollStudent execute(InputEnrollStudent input){
        var optionalStudent = studentRepository.findByRa(input.studentRa());

        if(optionalStudent.isEmpty()){
            throw new RuntimeException("pega aqui");
        }

        var student = optionalStudent.get();
        List<IDiscipline> enrollDisciplines = new ArrayList<>();
        List<IDiscipline> disenrollDisciplines = new ArrayList<>();
        for(String disciplineCode : input.disciplineCodes()){
            enrollDisciplines.add(disciplineRepository.findByCode(disciplineCode));
        }

        student.disciplines().forEach(d -> {
            if(!enrollDisciplines.contains(d)){
                disenrollDisciplines.add(d);
            }
        });

        student = new StudentBuilder(student.id(), student.name(), student.ra().toString(), student.shift())
                    .withCp(student.cp().value())
                    .withCr(student.cr().value())
                    .withCourse(student.course())
                    .withDisciplines(enrollDisciplines).build();

        studentRepository.update(student);
        for(IDiscipline discipline : disenrollDisciplines){
            calculateCoefficientsOfDiscipline.execute(discipline);
            disciplineRepository.update(discipline);
            dispatcher.notify(new StudentRemovedFromDiscipline(discipline, student));
        }

        for(IDiscipline discipline : student.disciplines()){
            calculateCoefficientsOfDiscipline.execute(discipline);
            disciplineRepository.update(discipline);
            dispatcher.notify(new StudentRegisteredInDiscipline(discipline, student));
        }
        return new OutputEnrollStudent();
    }
}
