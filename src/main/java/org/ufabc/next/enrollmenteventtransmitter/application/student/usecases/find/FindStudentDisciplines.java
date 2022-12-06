package org.ufabc.next.enrollmenteventtransmitter.application.student.usecases.find;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.exceptions.ResourceNotFoundException;
import org.ufabc.next.enrollmenteventtransmitter.domain.student.StudentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class FindStudentDisciplines {

    private final StudentRepository studentRepository;

    public FindStudentDisciplines(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public List<DisciplineResponse> execute(String ra) {
        if (ra == null) {
            throw new IllegalArgumentException("ra is null");
        }
        var student = studentRepository.findByRa(ra);

        if (student.isEmpty()) {
            throw new ResourceNotFoundException("student not found");
        }

        return student.get()
                .disciplines()
                .stream()
                .map(discipline ->
                        new DisciplineResponse(discipline.code(), discipline.name(),
                                discipline.course().name(),
                                discipline.theoryProfessor() == null ? null : discipline.theoryProfessor().name(),
                                discipline.practiceProfessor() == null ? null : discipline.practiceProfessor().name(),
                                discipline.vacancies(), discipline.subscribers(), discipline.shift(),
                                discipline.thresholdCr(), discipline.thresholdCp()))
                .toList();
    }
}
