package ru.aristov.park.services.user;

import ru.aristov.park.models.user.User;
import ru.aristov.park.services.CRUDService;

import java.util.List;

public interface UserService extends CRUDService<User> {
    List<User> findAllByFirstName(String firstName);
}