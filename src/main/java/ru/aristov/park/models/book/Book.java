package ru.aristov.park.models.book;

import ru.aristov.park.models.user.User;

import java.util.List;

public class Book {
    private Integer id;
    private String title;
    private String description;
    private List<User> users;

    public Book() {
    }

    public Book(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Book(String title, String description, List<User> users) {
        this.title = title;
        this.description = description;
        this.users = users;
    }

    public Book(Integer id, String title, String description, List<User> users) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int result = 19;

        if (this.title != null) {
            result = 31 * result + title.hashCode();
        }

        if (this.description != null) {
            result = 31 * result + description.hashCode();
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Book))
            return false;
        Book other = (Book) obj;
        boolean titleEquals = (this.title == null && other.title == null)
                || (this.title != null && this.title.equals(other.title));

        boolean descriptionEquals = (this.description == null && other.description == null)
                || (this.description != null && this.description.equals(other.description));

        return titleEquals && descriptionEquals;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", users=" + users +
                '}';
    }
}
