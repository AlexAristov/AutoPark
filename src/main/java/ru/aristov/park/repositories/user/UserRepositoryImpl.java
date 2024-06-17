package ru.aristov.park.repositories.user;

import ru.aristov.park.models.book.Book;
import ru.aristov.park.models.car.Car;
import ru.aristov.park.models.user.User;
import ru.aristov.park.utlils.DBUtils;

import java.sql.*;
import java.util.*;

public class UserRepositoryImpl implements UserRepository {
    private final Connection connection;
    // language=SQL
    private final String SQL_INSERT_INTO = "INSERT INTO \"user\" (first_name, last_name) VALUES (?, ?)";
    // language=SQL
    private final String SQL_UPDATE = "UPDATE \"user\" SET first_name = ?, last_name = ? WHERE id = ?";
    // language=SQL
    private final String SQL_SELECT_ALL_USER_WITH_CAR_AND_WITH_BOOK = "SELECT \n" +
            "\"user\".*, \n" +
            "\"book\".id as book_id, \"book\".title as title, \"book\".description as description,\n" +
            "\"car\".id as car_id, \"car\".model as model\n" +
            "FROM \"user\"\n" +
            "LEFT JOIN \"car\" ON \"car\".owner_id = \"user\".id\n" +
            "LEFT JOIN \"user_book\" ON \"user_book\".user_id = \"user\".id\n" +
            "LEFT JOIN \"book\" ON \"book\".id = \"user_book\".book_id";
    // language=SQL
    private final String SQL_SELECT_ALL_USER_WITH_CAR_AND_WITH_BOOK_BY_ID = "SELECT \n" +
            "\"user\".*, \n" +
            "\"book\".id as book_id, \"book\".title as title, \"book\".description as description,\n" +
            "\"car\".id as car_id, \"car\".model as model\n" +
            "FROM \"user\"\n" +
            "LEFT JOIN \"car\" ON \"car\".owner_id = \"user\".id\n" +
            "LEFT JOIN \"user_book\" ON \"user_book\".user_id = \"user\".id\n" +
            "LEFT JOIN \"book\" ON \"book\".id = \"user_book\".book_id\n" +
            "WHERE \"user\".id = ?";
    // language=SQL
    private final String SQL_SELECT_ALL_USER_WITH_CAR_AND_WITH_BOOK_BY_FIRST_NAME = "SELECT \n" +
            "\"user\".*, \n" +
            "\"book\".id as book_id, \"book\".title as title, \"book\".description as description,\n" +
            "\"car\".id as car_id, \"car\".model as model\n" +
            "FROM \"user\"\n" +
            "LEFT JOIN \"car\" ON \"car\".owner_id = \"user\".id\n" +
            "LEFT JOIN \"user_book\" ON \"user_book\".user_id = \"user\".id\n" +
            "LEFT JOIN \"book\" ON \"book\".id = \"user_book\".book_id\n" +
            "WHERE \"user\".first_name = ?";
    // language=SQL
    private final String SQL_DELETE_USER = "DELETE FROM \"user\" WHERE id = ?";

    public UserRepositoryImpl(Connection connection)  {
        this.connection = connection;

    }

    public UserRepositoryImpl() {
        try {
            connection = DBUtils.getDataSource().getConnection();
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users;
        Map<Integer, User> usersMap;

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USER_WITH_CAR_AND_WITH_BOOK);

            usersMap = getUsersJoinCarsAndJoinBooks(resultSet);

            users = new ArrayList<>(usersMap.values());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return users;
    }

    @Override
    public Optional<User> getById(Integer id) {
        Map<Integer, User> usersMap;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_USER_WITH_CAR_AND_WITH_BOOK_BY_ID);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            usersMap = getUsersJoinCarsAndJoinBooks(resultSet);

            if (!usersMap.isEmpty()) {
                User user = usersMap.get(id);

                if (user != null) {
                    return Optional.of(user);
                }
            }
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }

        return Optional.empty();
    }

    @Override
    public User create(User entity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO);
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(1, entity.getFirstName());
            int countUpdated = preparedStatement.executeUpdate();

            if (countUpdated > 0) {
                return entity;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return null;
    }

    @Override
    public User update(User entity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setInt(3, entity.getId());
            int countUpdated = preparedStatement.executeUpdate();

            if (countUpdated > 0) {
                Optional<User> userOptional = this.getById(entity.getId());

                if (userOptional.isPresent()) {
                    return userOptional.get();
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER);
            preparedStatement.setInt(1, id);

            int countDeleted = preparedStatement.executeUpdate();

            if (countDeleted > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public List<User> findAllByFirstName(String firstName) {
        List<User> users;
        Map<Integer, User> usersMap;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_USER_WITH_CAR_AND_WITH_BOOK_BY_FIRST_NAME);
            preparedStatement.setString(1, firstName);

            ResultSet resultSet = preparedStatement.executeQuery();

            usersMap = this.getUsersJoinCarsAndJoinBooks(resultSet);

            users = new ArrayList<>(usersMap.values());

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return users;
    }

    private Map<Integer, User> getUsersJoinCarsAndJoinBooks(ResultSet resultSetUsers) {
        Map<Integer, User> users = new HashMap<>();

        try {
            while(resultSetUsers.next()) {
                Integer userId = resultSetUsers.getInt("id");

                // add user
                if (!users.containsKey(userId)) {
                    String firstName = resultSetUsers.getString("first_name");
                    String lastName = resultSetUsers.getString("last_name");

                    List<Car> cars = new ArrayList<>();
                    List<Book> books = new ArrayList<>();
                    User user = new User(userId, firstName, lastName, cars, books);

                    users.put(userId, user);
                }

                Integer carId = resultSetUsers.getInt("car_id");

                // add cars for user
                if (carId != 0) {
                    String model = resultSetUsers.getString("model");
//                    Car car = new Car(carId, model, users.get(id));
                    Car car = new Car(carId, model);

                    if (!users.get(userId).getCars().contains(car)) {
                        users.get(userId).getCars().add(car);
                    }
                }

                Integer bookId = resultSetUsers.getInt("book_id");

                // add book for user
                if (bookId != 0) {
                    String title = resultSetUsers.getString("title");
                    String description = resultSetUsers.getString("description");

                    Book book = new Book(bookId, title, description);

                    if (!users.get(userId).getBooks().contains(book)) {
                        users.get(userId).getBooks().add(book);
                    }
                }
            }
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }

        return users;
    }
}
