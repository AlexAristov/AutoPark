package ru.aristov.park.services.user;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import ru.aristov.park.models.user.User;
import ru.aristov.park.repositories.user.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.assertj.core.api.Assertions.assertThat;

class UserServiceImplTest {
    private final UserRepository userRepository = mock(UserRepository.class);;

    private final UserServiceImpl userService = new UserServiceImpl(userRepository);

    @Test
    void getAll() {
        User user1 = new User("Alex", "Aristov");
        User user2 = new User("Anton", "Testov");
        List<User> users = Arrays.asList(user1, user2);

        BDDMockito.given(userService.getAll())
                .willReturn(users);

        List<User> obtainedUsers = userService.getAll();

        assertThat(obtainedUsers).isNotNull();
    }

    @Test
    void getById() {
        User user = new User("Alex", "Aristov");

        BDDMockito.given(userService.getById(anyInt()))
                .willReturn(Optional.of(user));

        Optional<User> obtainedUser = userService.getById(1);

        assertThat(obtainedUser).isNotNull();
    }

    @Test
    void create() {
        User userToSave = new User("Anton", "Testov");

        BDDMockito.given(userService.create(any(User.class)))
                .willReturn(new User("Anton", "Testov"));

        User user = userService.create(userToSave);

        assertThat(user).isNotNull();
    }

    @Test
    void update() {

    }

    @Test
    void deleteById() {
        BDDMockito.given(userService.deleteById(anyInt()))
                .willReturn(true);

        boolean isDeleted = userService.deleteById(1);

        assertThat(isDeleted).isTrue();
    }

    @Test
    void findAllByFirstName() {
        String firstName = "Alex";
        User user1 = new User("Alex", "Aristov");
        User user2 = new User("Alex", "Testov");
        List<User> users = Arrays.asList(user1, user2);

        BDDMockito.given(userService.findAllByFirstName(anyString()))
                .willReturn(users);

        List<User> obtainedUsers = userService.findAllByFirstName(firstName);

        assertThat(obtainedUsers).isNotNull();
    }
}