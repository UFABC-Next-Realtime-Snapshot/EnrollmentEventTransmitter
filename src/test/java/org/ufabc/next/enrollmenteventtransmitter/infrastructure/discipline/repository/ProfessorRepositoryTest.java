package org.ufabc.next.enrollmenteventtransmitter.infrastructure.discipline.repository;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@QuarkusTest
public class ProfessorRepositoryTest {
    @Inject
    EntityManager entityManager;

    @BeforeEach
    public void clean() {
        entityManager.createNativeQuery("DELETE FROM professors");
    }

    @Test
    public void shouldSave() {

    }
}
