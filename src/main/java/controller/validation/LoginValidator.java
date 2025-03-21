package controller.validation;

import common.Message;
import common.exception.ApplicationException;
import common.exception.DBException;
import dto.UserDTO;
import service.UserServices;

/**
 * LoginValidator class is used for controller.validation of Incoming data from form/UI.
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
        if (!userServices.getUserLoginCredentials(userDTO.getEmail()).getPassword().equals(userDTO.getPassword())) {
            throw new ApplicationException(Message.User.INCORRECT_LOGIN_CREDENTIALS);
        }
    }
}
