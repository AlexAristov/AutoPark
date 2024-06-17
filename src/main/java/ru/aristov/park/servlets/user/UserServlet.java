package ru.aristov.park.servlets.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aristov.park.models.user.User;
import ru.aristov.park.models.user.UserDTO;
import ru.aristov.park.services.user.UserService;
import ru.aristov.park.services.user.UserServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServlet extends HttpServlet {
    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserServlet() {
        this.userService = new UserServiceImpl();
    }

    public UserServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        String id = request.getParameter("id");
        String firstName = request.getParameter("firstName");

        List<User> users;

        // write User
        if (id != null) {
            Optional<User> userOptional = userService.getById(Integer.valueOf(id));

            if (userOptional.isPresent()) {
                writer.write(objectMapper.writeValueAsString(UserDTO.fromEntity(userOptional.get())));
            } else {
                writer.write(objectMapper.writeValueAsString("User id = " + id + "not found"));
            }
        }
        // write List<User>
        else {
            if (firstName != null) {
                users = userService.findAllByFirstName(firstName);
            } else {
                users = userService.getAll();
            }

            writer.write(objectMapper.writeValueAsString(UserDTO.fromEntities(users)));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        UserDTO userDTO = getUserDTOFromRequestContext(request);

        User user = userService.create(userDTO.toEntity());

        writer.write(objectMapper.writeValueAsString(UserDTO.fromEntity(user)) + " added");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        UserDTO userDTO = getUserDTOFromRequestContext(request);

        User user = userService.update(userDTO.toEntity());

        writer.write(objectMapper.writeValueAsString(UserDTO.fromEntity(user)) + " updated");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        Integer id = Integer.valueOf(request.getParameter("id"));

        boolean isDeleted = userService.deleteById(id);

        if (isDeleted) {
            writer.write("User id = " + id + " deleted");
        } else {
            writer.write("id not found");
        }
    }

    private  UserDTO getUserDTOFromRequestContext(HttpServletRequest request) throws IOException  {
        String requestJson = getRequestJson(request);
        return objectMapper.readValue(requestJson, UserDTO.class);
    }

    private String getRequestJson(HttpServletRequest request) throws IOException {
        return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }
}
