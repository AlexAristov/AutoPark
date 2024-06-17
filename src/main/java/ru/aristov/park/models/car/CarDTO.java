package ru.aristov.park.models.car;

import ru.aristov.park.models.user.User;

import java.util.ArrayList;
import java.util.List;

public class CarDTO {
    private Integer id;
    private String model;
    private User owner;

    public CarDTO() {
    }

    public CarDTO(String model, User owner) {
        this.model = model;
        this.owner = owner;
    }

    public CarDTO(String model) {
        this.model = model;
    }

    public CarDTO(Integer id, String model, User owner) {
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

    public Car toEntity() {
        return new Car(
            this.id,
            this.model,
            this.owner
        );
    }

    public static CarDTO fromEntity(Car car) {
        return new CarDTO(
            car.getId(),
            car.getModel(),
            car.getOwner()
        );
    }

    public static List<CarDTO> fromEntities(List<Car> cars) {
        List<CarDTO> carDTOList = new ArrayList<>();

        for (Car car: cars) {
            carDTOList.add(CarDTO.fromEntity(car));
        }

        return carDTOList;
    }

}
