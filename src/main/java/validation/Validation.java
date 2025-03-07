package validation;

import entity.User;
import services.UserServices;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * Validation class is used for validation of Incoming data from form/UI.
 * It throws ValidationException if the incoming data violets the constraints.
 */
public class Validation {
    private static Validator validate = new Validator();
    private static UserServices userServices = new UserServices();

    /**
     * This method is used to validate the incoming SignUp form data.
     * @param user - data to be validated
     * @throws ValidationException - thrown if incoming data violets the constraint
     */
    public static void validateSignUp(User user) throws ValidationException, SQLException {
        userServices = new UserServices();
        List<User> userList = userServices.getAllUsers(user.getRole().getId());

        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            throw new ValidationException("First name is Mandatory");
        }
        if (user.getFirstName().length() > 30) {
            throw new ValidationException("First name should be equal to 30 characters.");
        }
        if (user.getLastName() == null || user.getLastName().isBlank()) {
            throw new ValidationException("Last name is Mandatory");
        }
        if (user.getLastName().length() > 30) {
            throw new ValidationException("Last name should be equal to 30 characters.");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ValidationException("Email is Mandatory");
        }
        if (!validate.checkEmail(user.getEmail())) {
            throw new ValidationException("Please enter valid Email Address");
        }
        for (User u : userList) {
            if (Objects.equals(u.getEmail(), user.getEmail())) {
                throw new ValidationException("There already exits User with this email address. " +
                        "Please try again with different email address.");
            }
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new ValidationException("Password is mandatory");
        }
        if (!validate.checkPassword(user.getPassword())) {
            throw new ValidationException("Please enter valid Password. It should be between 8-40 characters and should be a " +
                    "combination of uppercase and lowercase letters, numbers, and special characters");
        }
        if (user.getPhoneNumber() == null || user.getPhoneNumber().isBlank()) {
            throw new ValidationException("Phone number is mandatory");
        }
        if (!validate.checkContactNo(user.getPhoneNumber())) {
            throw new ValidationException("Please enter valid Phone number. It should be combination of 10 numbers starting with 1-9 numbers.");
        }
        for (User u : userList) {
            if (Objects.equals(u.getPhoneNumber(), user.getPhoneNumber())) {
                throw new ValidationException("There already exits User with this Phone number. " +
                        "Please try again with different phone number");
            }
        }
    }

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
