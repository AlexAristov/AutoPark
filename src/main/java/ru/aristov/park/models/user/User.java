package ru.aristov.park.models.user;

import ru.aristov.park.models.book.Book;
import ru.aristov.park.models.car.Car;

import java.util.List;

public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private List<Car> cars;
    private List<Book> books;

    public User() {
    }

    public User(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String firstName, String lastName, List<Car> cars) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cars = cars;
    }

    public User(Integer id, String firstName, String lastName, List<Car> cars) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cars = cars;
    }

    public User(Integer id, String firstName, String lastName, List<Car> cars, List<Book> books) {
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
        return lastName;
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

    @Override
    public int hashCode() {
        int result = 27;

        if (this.firstName != null) {
            result = 38 * result + firstName.hashCode();
        }
        if (this.lastName != null) {
            result = 38 * result + lastName.hashCode();
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        boolean firstNameEquals = (this.firstName == null && other.firstName == null)
                || (this.firstName != null && this.firstName.equals(other.firstName));
        boolean lastNameEquals = (this.lastName == null && other.lastName == null)
                || (this.lastName != null && this.lastName.equals(other.lastName));

        return firstNameEquals && lastNameEquals;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cars=" + cars +
                '}';
    }
}
