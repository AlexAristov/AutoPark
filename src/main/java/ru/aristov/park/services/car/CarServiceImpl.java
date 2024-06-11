package ru.aristov.park.services.car;

import ru.aristov.park.models.car.Car;
import ru.aristov.park.repositories.car.CarRepository;
import ru.aristov.park.repositories.car.CarRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class CarServiceImpl implements CarService{
    private final CarRepository carRepository;

    public CarServiceImpl() {
        this.carRepository = new CarRepositoryImpl();
    }

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> getAll() {
        return carRepository.getAll();
    }

    @Override
    public Optional<Car> getById(Integer id) {
        return carRepository.getById(id);
    }

    @Override
    public List<Car> getByOwnerId(Integer ownerId) {
        return carRepository.getByOwnerId(ownerId);
    }

    @Override
    public Car create(Car entity) {
        return carRepository.create(entity);
    }

    @Override
    public Car update(Car entity) {
        return carRepository.update(entity);
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }
}
