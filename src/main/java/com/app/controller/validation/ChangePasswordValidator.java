package com.app.controller.validation;

import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.dto.UserDTO;
import com.app.service.UserServices;

import java.util.Objects;

public class ChangePasswordValidator {
    private static UserServices userServices = new UserServices();
    private static Validator validate = new Validator();

    public static void validatePassword(UserDTO userDTO) throws ApplicationException, DBException {
        UserDTO userFromDB = userServices.getUserLoginCredentials(userDTO.getEmail());

        if(!userServices.isUserValid(userDTO.getEmail())) {
            throw new ApplicationException(Message.User.NO_SUCH_USER);
        }
        if(!Objects.equals(userDTO.getPassword(), userFromDB.getPassword())) {
            throw new ApplicationException(Message.User.INCORRECT_PASSWORD);
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!validate.checkPassword(userDTO.getNewPassword())) {
            throw new ApplicationException(Message.User.INVALID_PASSWORD);
        }
        if(Objects.equals(userDTO.getPassword(), userDTO.getNewPassword())) {
            throw new ApplicationException(Message.User.SAME_PASSWORD);
        }
    }
}
