package ru.aristov.park.servlets.user;

import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.aristov.park.models.book.Book;
import ru.aristov.park.models.car.Car;
import ru.aristov.park.models.user.User;
import ru.aristov.park.models.user.UserDTO;
import ru.aristov.park.repositories.user.UserRepository;
import ru.aristov.park.services.user.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServletTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserServiceImpl userService = new UserServiceImpl(userRepository);

    private final StringWriter stringWriter = new StringWriter();
    private final PrintWriter printWriter = new PrintWriter(stringWriter);
    private final UserServlet servlet = new UserServlet(userService);
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);


    @BeforeEach
    void setUp() {
    }

    @Test
    @Order(1)
    void whenDoGetWithoutParameters() throws IOException {
        String responseContext = "[{\"id\":1,\"firstName\":\"Alex\",\"lastName\":\"Aristov\",\"cars\":[{\"id\":3,\"model\":\"VESTA\",\"owner\":null},{\"id\":1,\"model\":\"FOCUS\",\"owner\":null}],\"books\":[{\"id\":1,\"title\":\"1 book\",\"description\":\"1 description\",\"users\":null},{\"id\":2,\"title\":\"2 book\",\"description\":\"2 description\",\"users\":null}]},{\"id\":2,\"firstName\":\"Petr\",\"lastName\":\"Petrov\",\"cars\":[{\"id\":2,\"model\":\"CAMRY\",\"owner\":null}],\"books\":[{\"id\":1,\"title\":\"1 book\",\"description\":\"1 description\",\"users\":null}]},{\"id\":3,\"firstName\":\"Anton\",\"lastName\":\"Rogov\",\"cars\":[],\"books\":[{\"id\":4,\"title\":\"4 book\",\"description\":\"4 description\",\"users\":null}]}]";

        User user1 = new User(1,"Alex", "Aristov");
        User user2 = new User(2,"Petr", "Petrov");
        User user3 = new User(3,"Anton", "Rogov");

        Car car1 = new Car(1, "FOCUS");
        Car car2 = new Car(2, "CAMRY");
        Car car3 = new Car(3, "VESTA");
        user1.setCars(Arrays.asList(car3, car1));
        user2.setCars(Arrays.asList(car2));
        user3.setCars(new ArrayList<>());

        Book book1 = new Book(1, "1 book", "1 description");
        Book book2 = new Book(2, "2 book", "2 description");
        Book book4 = new Book(4, "4 book", "4 description");
        user1.setBooks(Arrays.asList(book1, book2));
        user2.setBooks(Arrays.asList(book1));
        user3.setBooks(Arrays.asList(book4));

        List<User> users = Arrays.asList(user1, user2, user3);


        BDDMockito.given(userService.getAll())
                .willReturn(users);

        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        assertEquals(responseContext, stringWriter.toString());
    }
    @Test
    @Order(2)
    void whenDoGetWithParameterId() throws IOException {
        String responseContext = "{\"id\":1,\"firstName\":\"Alex\",\"lastName\":\"Aristov\",\"cars\":[{\"id\":3,\"model\":\"VESTA\",\"owner\":null},{\"id\":1,\"model\":\"FOCUS\",\"owner\":null}],\"books\":[{\"id\":1,\"title\":\"1 book\",\"description\":\"1 description\",\"users\":null},{\"id\":2,\"title\":\"2 book\",\"description\":\"2 description\",\"users\":null}]}";

        Car car = new Car(3, "VESTA");
        Car car2 = new Car(1, "FOCUS");
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        cars.add(car2);
        User user = new User(1,"Alex", "Aristov");
        user.setCars(cars);

        Book book = new Book(1, "1 book", "1 description");
        Book book2 = new Book(2, "2 book", "2 description");
        List<Book> books = new ArrayList<>();
        books.add(book);
        books.add(book2);
        user.setBooks(books);

        Optional<User> obtainedUser = Optional.of(user);


        BDDMockito.given(userService.getById(anyInt()))
                .willReturn(obtainedUser);

        when(response.getWriter()).thenReturn(printWriter);
        when(request.getParameter("id")).thenReturn("1");

        servlet.doGet(request, response);

        assertEquals(responseContext, stringWriter.toString());
    }
    @Test
    @Order(3)
    void whenDoGetWithParameterFirstName() throws IOException {
        String responseContext = "[{\"id\":1,\"firstName\":\"Alex\",\"lastName\":\"Aristov\",\"cars\":[{\"id\":1,\"model\":\"FOCUS\",\"owner\":null},{\"id\":3,\"model\":\"VESTA\",\"owner\":null}],\"books\":[{\"id\":1,\"title\":\"1 book\",\"description\":\"1 description\",\"users\":null},{\"id\":2,\"title\":\"2 book\",\"description\":\"2 description\",\"users\":null}]}]";

        Car car = new Car(1, "FOCUS");
        Car car2 = new Car(3, "VESTA");
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        cars.add(car2);
        User user = new User(1,"Alex", "Aristov");
        user.setCars(cars);

        Book book = new Book(1, "1 book", "1 description");
        Book book2 = new Book(2, "2 book", "2 description");
        List<Book> books = new ArrayList<>();
        books.add(book);
        books.add(book2);
        user.setBooks(books);

        List<User> users = Arrays.asList(user);

        BDDMockito.given(userService.findAllByFirstName(anyString()))
                .willReturn(users);

        when(response.getWriter()).thenReturn(printWriter);
        when(request.getParameter("firstName")).thenReturn("Alex");

        servlet.doGet(request, response);

        assertEquals(responseContext, stringWriter.toString());
    }

    @Test
    @Order(4)
    void whenDoPostWithParameterThenReturnNewUser() throws IOException {
        String requestContext = """
            {
                "firstName": "Alex",
                "lastName": "Aristov"
            }
            """;
        String responseContext = "{\"id\":null,\"firstName\":\"Alex\",\"lastName\":\"Aristov\",\"cars\":null,\"books\":null} added";

        UserDTO userDTO = new UserDTO("Alex", "Aristov");
        User user = userDTO.toEntity();

        BDDMockito.given(userService.create(any(User.class)))
                .willReturn(user);

        when(response.getWriter()).thenReturn(printWriter);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(requestContext)));

        servlet.doPost(request, response);

        assertEquals(responseContext, stringWriter.toString());
    }

    @Test
    @Order(5)
    void whenDoPutWithParameterThenReturnUpdatedUser(){
    }

    @Test
    @Order(6)
    void doDelete() throws IOException {
        String responseContext = "User id = 1 deleted";

        BDDMockito.given(userService.deleteById(anyInt()))
                .willReturn(true);

        when(response.getWriter()).thenReturn(printWriter);
        when(request.getParameter("id")).thenReturn("1");

        servlet.doDelete(request, response);

        assertEquals(responseContext, stringWriter.toString());
    }
}