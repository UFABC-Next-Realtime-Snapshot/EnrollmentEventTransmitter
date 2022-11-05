package org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.repository;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.DisciplineRepository;
import org.ufabc.next.enrollmenteventtransmitter.domain.discipline.IDiscipline;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.commons.repository.CourseEntity;

@ApplicationScoped
public class JdbcDisciplineRepository implements DisciplineRepository {

    @Override
    public void add(IDiscipline discipline) {
        DisciplineEntity.persist(DisciplineEntity.toEntity(discipline));
    }

    @Override
    public void update(IDiscipline discipline) {
        // Essas verificacoes podem ser feitas em outra camada, o problema eh que
        // professor e curso nao sao entidades no sistema
        // pelo fato de serem objetos de valor nao possuem id logo para atualizar o
        // banco uma consulta deve ser feita antes para
        // capturar o id deles... Estamos seguindo DDD ? Nao e faz tempo, o problema nao
        // requer isso pois nao eh complexo o suficiente.
        // Faz sentido pensar em DDD quando a maioria do seu dominio nao tem
        // comportamento? Eu acho que nao.
        // A escolha de processar ranking de alunos e recalcular os coeficientes no
        // banco de dados matou a aplicacao ?
        // Talvez, mas DDD nao seria um canhao para matar uma formiga se as unicas
        // regras e casos de uso sao gerar rankings e calcular coeficientes?
        var optionalCourseEntity = CourseEntity.find("SELECT * FROM courses WHERE name = ?", discipline.course().name())
                .singleResultOptional();
        var optionalPracticeProfessorEntity = ProfessorEntity
                .find("SELECT * FROM professors WHERE name = ?", discipline.practiceProfessor().name())
                .singleResultOptional();
        var optionalTheoryProfessorEntity = ProfessorEntity
                .find("SELECT * FROM professors WHERE name = ?", discipline.theoryProfessor().name())
                .singleResultOptional();

        if (optionalCourseEntity.isEmpty() || optionalPracticeProfessorEntity.isEmpty()
                || optionalTheoryProfessorEntity.isEmpty()) {
            System.out.println("Deu ruim");
            return;
        }

        var course = (CourseEntity) optionalCourseEntity.get();
        var practice_professor = (ProfessorEntity) optionalPracticeProfessorEntity.get();
        var theory_professor = (ProfessorEntity) optionalTheoryProfessorEntity.get();

        DisciplineEntity.update(
                "UPDATE disciplines SET name = :name, course_id = :course_id, shift = :shift, vacancies = :vacancies, practice_professor_id = :practice_professor_id, theory_professor_id = :theory_professor_id WHERE code = :code",
                Map.of("name", discipline.name(), "course_id", course.id, "shift", discipline.shift().initial(),
                        "vacancies", discipline.vacancies(),
                        "practice_professor_id", practice_professor.id, "theory_professor_id", theory_professor.id));
    }

    @Override
    public IDiscipline findByCode(String code) {
        return DisciplineEntity.toModel((DisciplineEntity) DisciplineEntity
                .find("SELECT * FROM disciplines WHERE code = :code", Map.of("code", code)));
    }

    @Override
    public List<IDiscipline> findAll() {
        return DisciplineEntity.findAll().list().stream().map((e) -> DisciplineEntity.toModel((DisciplineEntity) e))
                .toList();
    }

}
