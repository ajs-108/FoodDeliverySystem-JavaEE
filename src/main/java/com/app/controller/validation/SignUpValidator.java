package com.app.controller.validation;

import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.dto.UserDTO;
import com.app.service.UserServices;

public class SignUpValidator {
    private static final int NAME_LENGTH = 30;
    private static final int EMAIL_LENGTH = 255;
    private static final int ADDRESS_LENGTH = 255;
    private static UserServices userServices = new UserServices();
    private static Validator validate = new Validator();

    public static void validate(UserDTO signUpDTO) throws ApplicationException, DBException {
        if (signUpDTO.getFirstName() == null || signUpDTO.getFirstName().isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (signUpDTO.getFirstName().length() > NAME_LENGTH) {
            throw new ApplicationException(Message.User.NAME_LENGTH);
        }
        if (signUpDTO.getLastName() == null || signUpDTO.getLastName().isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (signUpDTO.getLastName().length() > NAME_LENGTH) {
            throw new ApplicationException(Message.User.NAME_LENGTH);
        }
        if (signUpDTO.getEmail() == null || signUpDTO.getEmail().isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!validate.checkEmail(signUpDTO.getEmail())) {
            throw new ApplicationException(Message.User.INVALID_EMAIL);
        }
        if (signUpDTO.getEmail().length() > EMAIL_LENGTH) {
            throw new ApplicationException(Message.User.EMAIL_LENGTH);
        }
        if (userServices.isEmailExists(signUpDTO.getEmail(), signUpDTO.getRole().getRoleId())) {
            throw new ApplicationException(Message.User.EMAIL_EXISTS);
        }
        if (signUpDTO.getPassword() == null || signUpDTO.getPassword().isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!validate.checkPassword(signUpDTO.getPassword())) {
            throw new ApplicationException(Message.User.INVALID_PASSWORD);
        }
        if (signUpDTO.getPhoneNumber() == null || signUpDTO.getPhoneNumber().isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!validate.checkPhoneNo(signUpDTO.getPhoneNumber())) {
            throw new ApplicationException(Message.User.INVALID_PHONE_NUMBER);
        }
        if (userServices.isPhoneNumberExists(signUpDTO.getPhoneNumber(), signUpDTO.getRole().getRoleId())) {
            throw new ApplicationException(Message.User.PHONE_NUMBER_EXISTS);
        }
        if (signUpDTO.getAddress().length() >= ADDRESS_LENGTH) {
            throw new ApplicationException(Message.User.ADDRESS_LENGTH);
        }
    }
}
