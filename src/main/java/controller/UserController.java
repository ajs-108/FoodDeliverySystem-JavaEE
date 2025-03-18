package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import controller.validation.SignUpValidator;
import dto.APIResponse;
import dto.user_dto.UserSignUpDTO;
import common.Mapper;
import common.Roles;
import dto.user_dto.UserDTO;
import common.exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.UserServices;

import java.io.IOException;
import java.sql.SQLException;

public class UserController extends HttpServlet {
    private UserServices userServices;
    private ObjectMapper objectMapper;
    private APIResponse apiResponse;
    private Mapper mapper;

    @Override
    public void init() throws ServletException {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        apiResponse = new APIResponse();
        userServices = new UserServices();
        mapper = new Mapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        HttpSession session = req.getSession(false);

        try {
            UserDTO user = mapper.toDTO(userServices.getUser((String) session.getAttribute("user")));
            apiResponse.setData(user);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            apiResponse.setMessage(e.getMessage());
            resp.getWriter().println(objectMapper.writeValueAsString(apiResponse));
            return;
        }
        resp.getWriter().println(objectMapper.writeValueAsString(apiResponse));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        UserSignUpDTO userDTO = objectMapper.readValue(req.getReader(), UserSignUpDTO.class);
        HttpSession session = req.getSession(false);

        if (req.getServletPath().equals("/customer")) {
            userDTO.setRole(Roles.ROLE_CUSTOMER);
        } else if (req.getServletPath().equals("/deliveryPerson")) {
            userDTO.setRole(Roles.ROLE_DELIVERY_PERSON);
        } else if (req.getServletPath().equals("/admin")) {
            userDTO.setRole(Roles.ROLE_SUPER_ADMIN);
        }
        User user = mapper.toUser(userDTO);
        try {
            SignUpValidator.validate(user);
            userServices.updateUser(user, (int) session.getAttribute("userId"));
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            apiResponse.setMessage(e.getMessage());
            resp.getWriter().println(objectMapper.writeValueAsString(apiResponse));
            return;
        } catch (ValidationException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            apiResponse.setMessage(e.getMessage());
            resp.getWriter().println(objectMapper.writeValueAsString(apiResponse));
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        apiResponse.setMessage("Your details are updated Successfully");
        resp.getWriter().println(objectMapper.writeValueAsString(apiResponse));
    }
}
