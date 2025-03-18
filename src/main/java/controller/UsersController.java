package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dto.APIResponse;
import common.Mapper;
import common.Roles;
import dto.user_dto.UserDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import service.UserServices;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class UsersController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Mapper mapper = new Mapper();
        UserServices userServices = new UserServices();
        APIResponse apiResponse = new APIResponse();
        int roleId = 0;

        if (request.getServletPath().equals("/customers")) {
            roleId = Roles.ROLE_CUSTOMER.getId();
        } else if (request.getServletPath().equals("/deliveryPersons")) {
            roleId = Roles.ROLE_DELIVERY_PERSON.getId();
        }
        try {
            List<UserDTO> listOfUsers = userServices.getAllUsers(roleId)
                    .stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toList());
            apiResponse.setData(listOfUsers);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            apiResponse.setMessage(e.getMessage());
            response.getWriter().println(objectMapper.writeValueAsString(apiResponse));
            return;
        }
        response.getWriter().println(objectMapper.writeValueAsString(apiResponse));
    }
}
