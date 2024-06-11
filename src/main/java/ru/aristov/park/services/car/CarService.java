package ru.aristov.park.services.car;

import ru.aristov.park.models.car.Car;
import ru.aristov.park.services.CRUDService;

import java.util.List;

public interface CarService extends CRUDService<Car> {
    public List<Car> getByOwnerId(Integer ownerId);
}
