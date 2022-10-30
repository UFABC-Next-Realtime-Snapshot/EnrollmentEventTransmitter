package org.ufabc.next.enrollmenteventtransmitter.application.enrollmentrecord;

import org.ufabc.next.enrollmenteventtransmitter.application.EventDispatcher;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.Discipline;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.DisciplineRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EnrollmentRecordService {
    @Inject
    EventDispatcher dispatcher;

    @Inject
    StudentRepository studentRepository;

    @Inject
    DisciplineRepository disciplineRepository;

    private class InputRegisterEnrollmentRecord{
        public String ra;
        public List<String> codes;
    }

    private class EventEnrollmentRecordRegistered {
        List<String> codes;
    }

    public void registerEnrollmentRecord(InputRegisterEnrollmentRecord input) throws InvalidStudentException {
        Optional<IStudent> optional = studentRepository.findByRa(input.ra);

        if(optional.isEmpty()){
            throw new RuntimeException("não achei o vacilão");
        }

        var student = optional.get();
        List<Discipline> disciplines = new ArrayList<>();
        for(String code : input.codes){
            disciplines.add(disciplineRepository.findDisciplineByCode(code));
        }

        var newStudent = new StudentBuilder(student.name(), student.ra().toString(), student.shift())
                .withCourse(student.course())
                .withDisciplines(disciplines)
                .withCp(student.cp().value())
                .withCr(student.cr().value())
                .build();
        studentRepository.update(newStudent);

    }
}
