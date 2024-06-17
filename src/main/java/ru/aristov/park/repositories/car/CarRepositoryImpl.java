package ru.aristov.park.repositories.car;

import ru.aristov.park.models.car.Car;
import ru.aristov.park.utlils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarRepositoryImpl implements CarRepository{
    private final Connection connection;
    // language=SQL
    private final String SQL_SELECT_ALL = "SELECT * FROM \"car\"";
    // language=SQL
    private final String SQL_SELECT_ALL_BY_ID = "SELECT * FROM \"car\" WHERE id = ?";
    // language=SQL
    private final String SQL_INSERT_INTO = "INSERT INTO \"car\" (model) VALUES (?)";
    // language=SQL
    private final String SQL_UPDATE = "UPDATE \"car\" SET model = ? WHERE id = ?";
    // language=SQL
    private final String SQL_SELECT_ALL_BY_OWNER_ID = "SELECT * FROM \"car\" WHERE owner_id = ?";

    public CarRepositoryImpl(Connection connection)  {
        this.connection = connection;

    }

    public CarRepositoryImpl() {
        try {
            connection = DBUtils.getDataSource().getConnection();
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Car> getAll() {
        List<Car> cars = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

            while (resultSet.next()) {
               Integer id = resultSet.getInt("id");
               String model = resultSet.getString("model");
               Integer ownerId = resultSet.getInt("owner_id");

               Car car = new Car(id, model);

               cars.add(car);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cars;
    }

    @Override
    public Optional<Car> getById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String model = resultSet.getString("model");

                Car car = new Car(id, model);

                return Optional.of(car);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Car> getByOwnerId(Integer ownerId) {
        List<Car> cars = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_BY_OWNER_ID);
            preparedStatement.setInt(1, ownerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String model = resultSet.getString("model");

                Car car = new Car(id, model);

                cars.add(car);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cars;
    }

    @Override
    public Car create(Car entity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO);
            preparedStatement.setString(1, entity.getModel());
            int countUpdated = preparedStatement.executeUpdate();

            if (countUpdated != 0) {
                return entity;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Car update(Car entity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, entity.getModel());
            preparedStatement.setInt(2, entity.getId());
            int countUpdated = preparedStatement.executeUpdate();

            if (countUpdated != 0) {
                Optional<Car> carOptional = this.getById(entity.getId());

                if (carOptional.isPresent()) {
                    return carOptional.get();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }
}