package controller.validation;

import common.Message;
import common.exception.ApplicationException;
import common.exception.DBException;
import dto.UserDTO;
import service.UserServices;

public class SignUpValidator {
    private static final int NAME_LENGTH = 30;
    private static final int EMAIL_LENGTH = 255;
    private static final int ADDRESS_LENGTH = 255;

    public static void validate(UserDTO signUpDTO) throws ApplicationException, DBException {
        Validator validate = new Validator();
        UserServices userServices = new UserServices();

        if (signUpDTO.getFirstName() == null || signUpDTO.getFirstName().isBlank()) {
            throw new ApplicationException(Message.User.MANDATORY);
        }
        if (signUpDTO.getFirstName().length() > NAME_LENGTH) {
            throw new ApplicationException(Message.User.NAME_LENGTH);
        }
        if (signUpDTO.getLastName() == null || signUpDTO.getLastName().isBlank()) {
            throw new ApplicationException(Message.User.MANDATORY);
        }
        if (signUpDTO.getLastName().length() > NAME_LENGTH) {
            throw new ApplicationException(Message.User.NAME_LENGTH);
        }
        if (signUpDTO.getEmail() == null || signUpDTO.getEmail().isBlank()) {
            throw new ApplicationException(Message.User.MANDATORY);
        }
        if (!validate.checkEmail(signUpDTO.getEmail())) {
            throw new ApplicationException(Message.User.INVALID_EMAIL);
        }
        if (signUpDTO.getEmail().length() > EMAIL_LENGTH) {
            throw new ApplicationException(Message.User.EMAIL_LENGTH);
        }
        if (userServices.isEmailExists(signUpDTO.getEmail(), signUpDTO.getRole().getId())) {
            throw new ApplicationException(Message.User.EMAIL_EXISTS);
        }
        if (signUpDTO.getPassword() == null || signUpDTO.getPassword().isBlank()) {
            throw new ApplicationException(Message.User.MANDATORY);
        }
        if (!validate.checkPassword(signUpDTO.getPassword())) {
            throw new ApplicationException(Message.User.INVALID_PASSWORD);
        }
        if (signUpDTO.getPhoneNumber() == null || signUpDTO.getPhoneNumber().isBlank()) {
            throw new ApplicationException(Message.User.MANDATORY);
        }
        if (!validate.checkPhoneNo(signUpDTO.getPhoneNumber())) {
            throw new ApplicationException(Message.User.INVALID_PHONE_NUMBER);
        }
        if (userServices.isPhoneNumberExists(signUpDTO.getPhoneNumber(), signUpDTO.getRole().getId())) {
            throw new ApplicationException(Message.User.PHONE_NUMBER_EXISTS);
        }
        if(signUpDTO.getAddress().length() >= ADDRESS_LENGTH) {
            throw new ApplicationException(Message.User.ADDRESS_LENGTH);
        }
    }
}
