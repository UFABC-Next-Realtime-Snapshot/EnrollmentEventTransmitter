package org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc.framework;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class JdbcTemplateTest {
    private final FakeEntityRowMapper fakeEntityRowMapper = new FakeEntityRowMapper();

    @Inject
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void clean() {
        jdbcTemplate.update("DROP TABLE IF EXISTS fake_entity", statement -> {
        });

        jdbcTemplate.update("CREATE TABLE fake_entity (id serial PRIMARY KEY, " +
                        "data VARCHAR (255) NOT NULL, " +
                        "another_data VARCHAR (255), " +
                        "fake_data INT NOT NULL)",
                statement -> {
                });
    }

    @Test
    public void whenThrowExceptionShouldNotExecute() {
        Long id = 1L;
        FakeEntity aFakeEntity = new FakeEntity(id, "aData", "anotherData", 1);

        Exception exception = assertThrows(JdbcException.class,
                () -> failAdd(aFakeEntity));

        String expectedMessage = "Data conversion error";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        Optional<FakeEntity> fakeEntity = findById(id);

        assertFalse(fakeEntity.isPresent());

    }

    @Test
    public void shouldExecuteQueries() {
        Long id = 1L;
        FakeEntity aFakeEntity = new FakeEntity(id, "aData", "anotherData", 1);

        add(aFakeEntity);

        Optional<FakeEntity> fakeEntity = findById(id);

        assertTrue(fakeEntity.isPresent());
        assertEquals(aFakeEntity, fakeEntity.get());
    }

    @Test
    public void shouldNotReturnWhenEntityNoExist() {
        Long id = 1000L;

        Optional<FakeEntity> fakeEntity = findById(id);

        assertTrue(fakeEntity.isEmpty());
    }

    @Test
    public void shouldReturnAll() {
        List<FakeEntity> aFakeEntities = List.of(
                new FakeEntity(null, "aData", "anotherData", 1),
                new FakeEntity(null, "aData2", "anotherData2", 2),
                new FakeEntity(null, "aData3", "anotherData3", 3)
        );

        aFakeEntities.forEach(this::add);

        List<FakeEntity> fakeEntities = listAll();

        assertEquals(aFakeEntities.size(), fakeEntities.size());
    }

    @Test
    public void shouldNotReturnEmptyWhenEntitiesNoExist() {
        List<FakeEntity> fakeEntities = listAll();

        assertEquals(0, fakeEntities.size());
    }

    @Test
    public void shouldExecuteTransactionStatement() {
        List<FakeEntity> fakeEntitiesEmpty = listAll();

        assertEquals(0, fakeEntitiesEmpty.size());

        updateTransaction();

        List<FakeEntity> fakeEntities = listAll();

        assertEquals(3, fakeEntities.size());
    }

    @Test
    public void whenThrowExceptionShouldRollbackTransaction() {
        List<FakeEntity> fakeEntitiesEmpty = listAll();

        assertEquals(0, fakeEntitiesEmpty.size());

        Exception exception = assertThrows(JdbcException.class,
                this::failUpdateTransaction);

        String expectedMessage = "Data conversion error";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        List<FakeEntity> fakeEntities = listAll();

        assertEquals(0, fakeEntities.size());
    }

    private void add(FakeEntity fakeEntity) {
        jdbcTemplate.update("INSERT INTO fake_entity (data, another_data, fake_data) VALUES (?, ?, ?)",
                (statement) -> {
                    statement.setString(1, fakeEntity.data());
                    statement.setString(2, fakeEntity.anotherData());
                    statement.setInt(3, fakeEntity.fakeData());
                });
    }

    private void failAdd(FakeEntity fakeEntity) {
        jdbcTemplate.update("INSERT INTO fake_entity (data, another_data, fake_data) VALUES (?, ?, ?)",
                (statement) -> {
                    statement.setString(2, fakeEntity.data());
                    statement.setString(3, fakeEntity.anotherData());
                    statement.setInt(1, fakeEntity.fakeData());
                });
    }

    private Optional<FakeEntity> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate
                .queryForObject("SELECT * FROM fake_entity WHERE id = ?",
                        (statement) -> statement.setLong(1, id), fakeEntityRowMapper));
    }

    private List<FakeEntity> listAll() {
        return jdbcTemplate.queryForList("SELECT * FROM fake_entity",
                statement -> {
                },
                fakeEntityRowMapper);
    }

    private final FakeEntity aFakeEntity = new FakeEntity(null, "aData", "anotherData", 1);

    private final TransactionOperation transactionOne = new TransactionOperation("INSERT INTO fake_entity (data, another_data, fake_data) VALUES (?, ?, ?)",
            (statement) -> {
                statement.setString(1, aFakeEntity.data());
                statement.setString(2, aFakeEntity.anotherData());
                statement.setInt(3, aFakeEntity.fakeData());
            });

    private final TransactionOperation transactionTwo = new TransactionOperation("INSERT INTO fake_entity (data, another_data, fake_data) VALUES (?, ?, ?)",
            (statement) -> {
                statement.setString(1, aFakeEntity.data() + "1");
                statement.setString(2, aFakeEntity.anotherData() + "1");
                statement.setInt(3, aFakeEntity.fakeData() + 1);
            });

    private final TransactionOperation transactionTree = new TransactionOperation("INSERT INTO fake_entity (data, another_data, fake_data) VALUES (?, ?, ?)",
            (statement) -> {
                statement.setString(1, aFakeEntity.data() + "2");
                statement.setString(2, aFakeEntity.anotherData() + "2");
                statement.setInt(3, aFakeEntity.fakeData() + 2);
            });

    private final TransactionOperation transactionFour = new TransactionOperation("INSERT INTO fake_entity (data, another_data, fake_data) VALUES (?, ?, ?)",
            (statement) -> {
                statement.setString(3, aFakeEntity.data() + "2");
                statement.setString(2, aFakeEntity.anotherData() + "2");
                statement.setInt(1, aFakeEntity.fakeData() + 2);
            });

    private void updateTransaction() {
        jdbcTemplate.transactionUpdates(transactionOne, transactionTwo, transactionTree);
    }

    private void failUpdateTransaction() {
        jdbcTemplate.transactionUpdates(transactionOne, transactionFour, transactionTree);
    }
}
