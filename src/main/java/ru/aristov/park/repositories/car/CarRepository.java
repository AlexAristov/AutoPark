package ru.aristov.park.repositories.car;

import ru.aristov.park.models.car.Car;
import ru.aristov.park.repositories.CRUDRepository;

import java.util.List;

public interface CarRepository extends CRUDRepository<Car> {
    public List<Car> getByOwnerId(Integer ownerId);
}
