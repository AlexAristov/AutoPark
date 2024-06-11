package ru.aristov.park.repositories.user;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import ru.aristov.park.models.user.User;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryImplTest {
    private final UserRepository userRepository = new UserRepositoryImpl(dataSource.getConnection());
    private final static HikariDataSource dataSource;
    @Container
    private static JdbcDatabaseContainer container =
            new PostgreSQLContainer(DockerImageName.parse("postgres:latest"))
                    .withUsername("postgres")
                    .withUsername("1qazxsw2")
                    .withInitScript("migration.sql");


    static {
        container.start();

        dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(container.getJdbcUrl());
        dataSource.setUsername(container.getUsername());
        dataSource.setPassword(container.getPassword());

        dataSource.setMinimumIdle(5);
        dataSource.setMaximumPoolSize(50);
    }

    UserRepositoryImplTest() throws SQLException {
    }


    @AfterAll
    static void stopContainer() {
        container.stop();
    }

    @Test
    @Order(1)
    void getAll() {
        User user1 = new User("Anton", "Test");
        User user2 = new User("Victor", "Test");
        User user3 = new User("Sergey", "Test");

        List<User> usersExpected = Arrays.asList(
                userRepository.create(user1),
                userRepository.create(user2),
                userRepository.create(user3)
        );

        List<User> users = userRepository.getAll();

        Assertions.assertTrue(users.containsAll(usersExpected));
    }

    @Test
    @Order(2)
    void getById() {
        User userExpected = new User("Alex", "Aristov");

        Optional<User> user = userRepository.getById(1);

        assertEquals(userExpected, user.get());
        assertThat(user).isNotNull();
    }

    @Test
    @Order(3)
    void findAllByFirstName() {
        List<User> user = userRepository.findAllByFirstName("Alex");

        assertThat(user).isNotNull();
    }

    @Test
    @Order(4)
    void create() {
        User userExpected = new User("Artem", "Test");
        User user = userRepository.create(new User("Artem", "Test"));

        assertEquals(userExpected, user);
        assertThat(user).isNotNull();
    }

    @Test
    @Order(5)
    void update() {
        User user = new User(1, "Alex_test", "Aristov_test");
        User userUpdated = userRepository.update(user);

        assertEquals("Alex_test", userUpdated.getFirstName());
        assertEquals("Aristov_test", userUpdated.getLastName());
        assertThat(user).isNotNull();
    }

    @Test
    @Order(6)
    void deleteById() {
        boolean isDeleted = userRepository.deleteById(3);

        assertEquals(true, isDeleted);
    }
}