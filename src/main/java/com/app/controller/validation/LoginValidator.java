package com.app.controller.validation;

import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.dto.UserDTO;
import com.app.service.UserServices;

import java.util.Objects;

/**
 * LoginValidator class is used for com.app.controller.validation of Incoming data from form/UI.
 * It throws ApplicationException if the incoming data violets the constraints.
 */
public class LoginValidator {
    private static UserServices userServices = new UserServices();

    /**
     * This method is used to validate the incoming SignUp form data.
     *
     * @param userDTO - data to be validated
     * @throws ApplicationException - thrown if incoming data violets the constraint
     */
    public static void validateLogin(UserDTO userDTO) throws ApplicationException, DBException {
        if(!userServices.isUserValid(userDTO.getEmail())) {
            throw new ApplicationException(Message.User.NO_SUCH_USER);
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!Objects.equals(userServices.getUserLoginCredentials(userDTO.getEmail()).getPassword(), userDTO.getPassword())) {
            throw new ApplicationException(Message.User.INCORRECT_LOGIN_CREDENTIALS);
        }
    }
}
