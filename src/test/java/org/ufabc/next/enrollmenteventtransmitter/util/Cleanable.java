package org.ufabc.next.enrollmenteventtransmitter.util;

import org.junit.jupiter.api.BeforeEach;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

public abstract class Cleanable {
    @Inject
    EntityManager entityManager;

    @BeforeEach
    @Transactional
    public void clean() {
        //--force
        var statement = entityManager.createNativeQuery("SHOW TABLES");

        var resultSet = statement.getResultList();
        if (resultSet.isEmpty()) {
            return;
        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        resultSet.forEach(result -> {
            var data = (Object[]) result;
            var table = (String) data[0];
            if (table.contains("CHANGELOG")) {
                return;
            }
            entityManager.createNativeQuery(String.format("TRUNCATE TABLE %s RESTART IDENTITY", table))
                    .executeUpdate();
        });
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }
}
