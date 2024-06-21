package ru.aristov.park.servlets.car;

import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.aristov.park.models.car.Car;
import ru.aristov.park.models.car.CarDTO;
import ru.aristov.park.repositories.car.CarRepository;
import ru.aristov.park.services.car.CarServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Testcontainers
class CarServletTest {
    private final CarRepository carRepository = mock(CarRepository.class);
    private final CarServiceImpl carService = new CarServiceImpl(carRepository);

    private final StringWriter stringWriter = new StringWriter();
    private final PrintWriter printWriter = new PrintWriter(stringWriter);
    private final CarServlet servlet = new CarServlet(carService);
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);


    @BeforeEach
    void setUp() {
    }

    @Test
    void doGet() throws IOException, ServletException {
//        String responseContext = "[{\"id\":2,\"model\":\"CAMRY\",\"owner\":null},{\"id\":3,\"model\":\"VESTA\",\"owner\":null},{\"id\":4,\"model\":\"CROSS\",\"owner\":null},{\"id\":1,\"model\":\"FOCUS\",\"owner\":null}]";
//
//        List<Car> cars = Arrays.asList(
//                new Car(2, "CAMRY"),
//                new Car(3, "VESTA"),
//                new Car(4, "CROSS"),
//                new Car(1, "FOCUS")
//        );
//
//        BDDMockito.given(carService.getAll())
//                .willReturn(cars);
//
//        when(response.getWriter()).thenReturn(printWriter);
//
//        servlet.doGet(request, response);
//
//        assertEquals(responseContext, stringWriter.toString());
    }

    @Test
    void doPost() throws IOException, ServletException {
        String requestContext = """
            {
                 "model": "KAMAZ"
            }
            """;
        String responseContext = "{\"id\":null,\"model\":\"KAMAZ\",\"owner\":null}";

        CarDTO carDTO = new CarDTO("KAMAZ");
        Car car = carDTO.toEntity();

        BDDMockito.given(carService.create(any(Car.class)))
                .willReturn(car);

        when(response.getWriter()).thenReturn(printWriter);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(requestContext)));

        servlet.doPost(request, response);

        assertEquals(responseContext, stringWriter.toString());
    }
}