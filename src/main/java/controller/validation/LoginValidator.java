package controller.validation;

import common.exception.ValidationException;
import model.User;
import service.UserServices;

import java.sql.SQLException;

/**
 * LoginValidator class is used for controller.validation of Incoming data from form/UI.
 * It throws ValidationException if the incoming data violets the constraints.
 */
public class LoginValidator {
    private static Validator validate = new Validator();
    private static UserServices userServices = new UserServices();

    /**
     * This method is used to validate the incoming SignUp form data.
     *
     * @param user - data to be validated
     * @throws ValidationException - thrown if incoming data violets the constraint
     */
    public static void validateLogin(User user) throws ValidationException, SQLException {
        User userFromDB = userServices.getUser(user.getEmail());

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new ValidationException("Email is Mandatory");
        }
        if (!validate.checkEmail(user.getEmail())) {
            throw new ValidationException("Please enter valid Email Address");
        }
        if (userFromDB == null) {
            throw new ValidationException("User not Found.");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new ValidationException("Password is mandatory");
        }
        if (!userFromDB.getPassword().equals(user.getPassword())) {
            throw new ValidationException("Login credentials are incorrect. Please try again");
        }
    }
}
