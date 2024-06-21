package ru.aristov.park.servlets.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aristov.park.models.car.Car;
import ru.aristov.park.models.car.CarDTO;
import ru.aristov.park.services.car.CarService;
import ru.aristov.park.services.car.CarServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class CarServlet extends HttpServlet {
    private final CarService carService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CarServlet() {
        this.carService = new CarServiceImpl();
    }

    public CarServlet(CarService carService) {
        this.carService = carService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        List<Car> cars = carService.getAll();

//        writer.write(objectMapper.writeValueAsString(CarDTO.fromEntities(cars)));
        writer.write("Hello");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        CarDTO carDTO = getCarDTOFromRequestContext(request);

        Car car = carService.create(carDTO.toEntity());

        writer.write(objectMapper.writeValueAsString(car));
    }

    private CarDTO getCarDTOFromRequestContext(HttpServletRequest request) throws IOException  {
        String requestJson = getRequestJson(request);
        return objectMapper.readValue(requestJson, CarDTO.class);
    }

    private String getRequestJson(HttpServletRequest request) throws IOException {
        return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }
}
