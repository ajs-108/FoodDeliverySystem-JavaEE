package controller.validation;

import common.Message;
import common.exception.ApplicationException;
import common.exception.DBException;
import dto.UserDTO;

public class UserUpdateValidator {
    private static final int NAME_LENGTH = 30;
    private static final int ADDRESS_LENGTH = 255;

    public static void validate(UserDTO userDTO) throws ApplicationException, DBException {
        Validator validate = new Validator();

        if (userDTO.getFirstName() == null || userDTO.getFirstName().isBlank()) {
            throw new ApplicationException(Message.User.MANDATORY);
        }
        if (userDTO.getFirstName().length() > NAME_LENGTH) {
            throw new ApplicationException(Message.User.NAME_LENGTH);
        }
        if (userDTO.getLastName() == null || userDTO.getLastName().isBlank()) {
            throw new ApplicationException(Message.User.MANDATORY);
        }
        if (userDTO.getLastName().length() > NAME_LENGTH) {
            throw new ApplicationException(Message.User.NAME_LENGTH);
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().isBlank()) {
            throw new ApplicationException(Message.User.MANDATORY);
        }
        if (!validate.checkPassword(userDTO.getPassword())) {
            throw new ApplicationException(Message.User.INVALID_PASSWORD);
        }
        if (userDTO.getPhoneNumber() == null || userDTO.getPhoneNumber().isBlank()) {
            throw new ApplicationException(Message.User.MANDATORY);
        }
        if (!validate.checkPhoneNo(userDTO.getPhoneNumber())) {
            throw new ApplicationException(Message.User.INVALID_PHONE_NUMBER);
        }
        if (userDTO.getAddress().length() >= ADDRESS_LENGTH) {
            throw new ApplicationException(Message.User.ADDRESS_LENGTH);
        }
    }
}


