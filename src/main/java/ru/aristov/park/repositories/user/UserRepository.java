package ru.aristov.park.repositories.user;

import ru.aristov.park.models.user.User;
import ru.aristov.park.repositories.CRUDRepository;

import java.util.List;

public interface UserRepository extends CRUDRepository<User> {
    List<User> findAllByFirstName(String firstName);
}
