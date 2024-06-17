package ru.aristov.park.repositories.car;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.aristov.park.models.car.Car;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CarRepositoryImplTest {
    private final CarRepository carRepository = new CarRepositoryImpl(dataSource.getConnection());
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

    CarRepositoryImplTest() throws SQLException {
    }

    @AfterAll
    static void stopContainer() {
        container.stop();
    }

    @Test
    @Order(1)
    void getAll() {
        Car car1 = new Car("TEST_MODEL_1");
        Car car2 = new Car("TEST_MODEL_2");
        Car car3 = new Car("TEST_MODEL_3");

        List<Car> carsExpected = Arrays.asList(
            carRepository.create(car1),
            carRepository.create(car2),
            carRepository.create(car3)
        );

        List<Car> cars = carRepository.getAll();

        Assertions.assertTrue(cars.containsAll(carsExpected));
    }

    @Test
    @Order(2)
    void getById() {
        Car carExpected = new Car("FOCUS");

        Optional<Car> car = carRepository.getById(1);

        assertEquals(carExpected, car.get());
        assertThat(car).isNotNull();
    }

    @Test
    @Order(3)
    void getByOwnerId() {
        List<Car> carsExpected = Arrays.asList(new Car("FOCUS"), new Car("VESTA"));

        List<Car> cars = carRepository.getByOwnerId(1);

        assertThat(cars).isNotNull();
        assertThat(cars).isEqualTo(carsExpected);
    }

    @Test
    @Order(4)
    void create() {
        Car carExpected = new Car("TEST_MODEL");
        Car car = carRepository.create(new Car("TEST_MODEL"));

        assertEquals(carExpected, car);
        assertThat(car).isNotNull();
    }

    @Test
    @Order(5)
    void update() {
        Car car = new Car(1, "FOCUS 3");
        Car carUpdated = carRepository.update(car);

        assertEquals("FOCUS 3", carUpdated.getModel());
        assertThat(carUpdated).isNotNull();
    }

    @Test
    @Order(6)
    void deleteById() {
    }
}