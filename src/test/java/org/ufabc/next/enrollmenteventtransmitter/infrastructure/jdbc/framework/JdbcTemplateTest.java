package org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc.framework;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    private void add(FakeEntity fakeEntity) {
        jdbcTemplate.update("INSERT INTO fake_entity (data, another_data, fake_data) VALUES (?, ?, ?)",
                (statement) -> {
                    statement.setString(1, fakeEntity.data());
                    statement.setString(2, fakeEntity.anotherData());
                    statement.setInt(3, fakeEntity.fakeData());
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

    @Test
    public void shouldAddAndFind() {
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
}
