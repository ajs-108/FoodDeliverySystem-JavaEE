package controller.validation;

import common.Message;
import common.exception.ApplicationException;
import common.exception.DBException;
import dto.user_dto.UserLoginDTO;
import model.User;
import service.UserServices;

import java.lang.reflect.Member;

/**
 * LoginValidator class is used for controller.validation of Incoming data from form/UI.
 * It throws ApplicationException if the incoming data violets the constraints.
 */
public class LoginValidator {
    private static Validator validate = new Validator();
    private static UserServices userServices = new UserServices();

    /**
     * This method is used to validate the incoming SignUp form data.
     *
     * @param user - data to be validated
     * @throws ApplicationException - thrown if incoming data violets the constraint
     */
    public static void validateLogin(UserLoginDTO user) throws ApplicationException, DBException {
        User userFromDB = userServices.getUser(user.getEmail());

        if(userFromDB == null) {
            throw new ApplicationException(Message.User.INVALID_USER);
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new ApplicationException(Message.User.MANDATORY);
        }
        if (!validate.checkEmail(user.getEmail())) {
            throw new ApplicationException(Message.User.INVALID_EMAIL);
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new ApplicationException(Message.User.MANDATORY);
        }
        if (!userFromDB.getPassword().equals(user.getPassword())) {
            throw new ApplicationException(Message.User.INCORRECT_LOGIN_CREDENTIALS);
        }
    }
}
