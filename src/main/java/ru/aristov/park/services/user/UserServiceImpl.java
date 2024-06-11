package ru.aristov.park.services.user;

import ru.aristov.park.models.book.Book;
import ru.aristov.park.models.car.Car;
import ru.aristov.park.models.user.User;
import ru.aristov.park.repositories.user.UserRepository;
import ru.aristov.park.repositories.user.UserRepositoryImpl;
import ru.aristov.park.services.book.BookService;
import ru.aristov.park.services.book.BookServiceImpl;
import ru.aristov.park.services.car.CarService;
import ru.aristov.park.services.car.CarServiceImpl;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final CarService carService = new CarServiceImpl();
    private final BookService bookService = new BookServiceImpl();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserServiceImpl() {
        this.userRepository = new UserRepositoryImpl();
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public Optional<User> getById(Integer id) {
        return userRepository.getById(id);
    }

    @Override
    public User create(User entity) {
        return userRepository.create(entity);
    }

    @Override
    public User update(User entity) {
        List<Car> cars = entity.getCars();
        List<Book> books = entity.getBooks();

        if (cars != null) {
            for (Car car: cars) {
                carService.update(car);
            }
        }

        if (books != null) {
            for (Book book: books) {
                bookService.update(book);
            }
        }

        return userRepository.update(entity);
    }

    @Override
    public boolean deleteById(Integer id) {
        return userRepository.deleteById(id);
    }

    @Override
    public List<User> findAllByFirstName(String firstName) {
        return userRepository.findAllByFirstName(firstName);
    }
}
