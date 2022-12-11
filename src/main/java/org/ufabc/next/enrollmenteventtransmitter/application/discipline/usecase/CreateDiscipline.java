package org.ufabc.next.enrollmenteventtransmitter.application.discipline.usecase;

import org.jboss.logging.Logger;
import org.ufabc.next.enrollmenteventtransmitter.domain.commons.exceptions.ResourceNotFoundException;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.Course;
import org.ufabc.next.enrollmenteventtransmitter.domain.course.CourseRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.*;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class CreateDiscipline {

    private static final Logger LOGGER = Logger.getLogger(CreateDiscipline.class);
    private final DisciplineRepository disciplineRepository;
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;

    public CreateDiscipline(DisciplineRepository disciplineRepository,
                            CourseRepository courseRepository, ProfessorRepository professorRepository) {
        this.disciplineRepository = disciplineRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
    }

    @Transactional
    public void execute(IDiscipline discipline) {
        if (disciplineRepository.findByCode(discipline.code()).isPresent()) {
            LOGGER.info(String.format("discipline %s already exist", discipline.code()));
            return;
        }

        Course course = courseOf(discipline);

        Professor practiceProfessor = practiceProfessorOf(discipline);
        Professor theoryProfessor = theoryProfessorOf(discipline);

        disciplineRepository.add(Discipline.aDiscipline()
                .withCode(discipline.code())
                .withName(discipline.name())
                .withTheoryProfessor(theoryProfessor)
                .withPracticeProfessor(practiceProfessor)
                .withCourse(course)
                .withVacancies(discipline.vacancies())
                .withShift(discipline.shift())
                .build());
    }

    private Course courseOf(IDiscipline discipline) {
        var course = courseRepository.findByName(discipline.course().name());
        if (course.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Course %s not found",
                    discipline.course().name()));
        }
        return course.get();
    }

    private Professor practiceProfessorOf(IDiscipline discipline) {
        if (discipline.practiceProfessor() == null) {
            return null;
        }
        var professor = professorRepository.findByName(discipline.practiceProfessor().name());
        if (professor.isEmpty()) {
            return professorRepository.add(discipline.practiceProfessor());
        }
        return professor.get();
    }

    private Professor theoryProfessorOf(IDiscipline discipline) {
        if (discipline.theoryProfessor() == null) {
            return null;
        }
        var professor = professorRepository.findByName(discipline.theoryProfessor().name());
        if (professor.isEmpty()) {
            return professorRepository.add(discipline.theoryProfessor());
        }
        return professor.get();
    }
}
