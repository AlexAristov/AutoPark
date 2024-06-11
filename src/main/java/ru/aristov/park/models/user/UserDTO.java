package ru.aristov.park.models.user;

import ru.aristov.park.models.book.Book;
import ru.aristov.park.models.car.Car;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private List<Car> cars;
    private List<Book> books;

    public UserDTO() {
    }

    public UserDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDTO(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDTO(Integer id, String firstName, String lastName, List<Car> cars) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cars = cars;
    }

    public UserDTO(Integer id, String firstName, String lastName, List<Car> cars, List<Book> books) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cars = cars;
        this.books = books;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public User toEntity() {
        return new User(
                this.id,
                this.firstName,
                this.lastName,
                this.cars,
                this.books
        );
    }

    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getCars(),
                user.getBooks()
        );
    }

    public static List<UserDTO> fromEntities(List<User> users) {
        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user: users) {
            userDTOList.add(UserDTO.fromEntity(user));
        }

        return userDTOList;
    }
}
