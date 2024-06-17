package ru.aristov.park.models.car;

import ru.aristov.park.models.user.User;

public class Car {
    private Integer id;
    private String model;
    private User owner;

    public Car() {
    }

    public Car(String model) {
        this.model = model;
    }

    public Car(Integer id, String model) {
        this.id = id;
        this.model = model;
    }

    public Car(String model, User owner) {
        this.model = model;
        this.owner = owner;
    }

    public Car(Integer id, String model, User owner) {
        this.id = id;
        this.model = model;
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public int hashCode() {
        int result = 27;

        if (this.model != null) {
            result = 38 * result + model.hashCode();
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Car))
            return false;
        Car other = (Car) obj;
        boolean modelEquals = (this.model == null && other.model == null)
                || (this.model != null && this.model.equals(other.model));

        return modelEquals;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", owner=" + owner +
                '}';
    }
}
