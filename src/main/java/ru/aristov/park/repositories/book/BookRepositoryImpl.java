package ru.aristov.park.repositories.book;

import ru.aristov.park.models.book.Book;
import ru.aristov.park.utlils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryImpl implements BookRepository{
    // language=SQL
    private final String SQL_UPDATE = "UPDATE \"book\" SET title = ?, description = ? WHERE id = ?";
    // language=SQL
    private final String SQL_SELECT_ALL_BOOKS_BY_OWNER_ID = "SELECT \n" +
            "\"user\".*, \n" +
            "\"book\".id as book_id, \"book\".title as title, \"book\".description as description\n" +
            "FROM \"user\"\n" +
            "LEFT JOIN \"user_book\" ON \"user_book\".user_id = \"user\".id\n" +
            "LEFT JOIN \"book\" ON \"book\".id = \"user_book\".book_id\n" +
            "WHERE \"user\".id = ?";
    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public Optional<Book> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Book create(Book entity) {
        return null;
    }

    @Override
    public Book update(Book entity) {
        try {
            Connection connection = DBUtils.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setInt(3, entity.getId());

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
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public List<Book> getByOwnerId(Integer ownerId) {
        List<Book> books = new ArrayList<>();
        try {
            Connection connection = DBUtils.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_BOOKS_BY_OWNER_ID);
            preparedStatement.setInt(1, ownerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");

                Book book = new Book(id, title, description);

                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }
}
