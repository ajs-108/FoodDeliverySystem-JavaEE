package com.app.controller.validation;

import com.app.common.Message;
import com.app.common.enums.AccountStatus;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.dto.UserDTO;
import com.app.service.UserServices;

import java.util.Objects;

public class UserValidator {
    private static final int NAME_LENGTH = 30;
    private static final int EMAIL_LENGTH = 255;
    private static final int ADDRESS_LENGTH = 255;
    private static UserServices userServices = new UserServices();
    private static Validator validate = new Validator();
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
        if (userServices.getUser(userDTO.getEmail()).getAccountStatus() == AccountStatus.DEACTIVATED) {
            throw new ApplicationException(Message.User.ACCOUNTED_DEACTIVATED);
        }
    }

    public static void validateSignUp(UserDTO signUpDTO) throws ApplicationException, DBException {
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

    public static void validateUpdate(UserDTO userDTO) throws ApplicationException, DBException {

        if (userDTO.getFirstName() == null || userDTO.getFirstName().isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (userDTO.getFirstName().length() > NAME_LENGTH) {
            throw new ApplicationException(Message.User.NAME_LENGTH);
        }
        if (userDTO.getLastName() == null || userDTO.getLastName().isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (userDTO.getLastName().length() > NAME_LENGTH) {
            throw new ApplicationException(Message.User.NAME_LENGTH);
        }
        if (userDTO.getPhoneNumber() == null || userDTO.getPhoneNumber().isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!validate.checkPhoneNo(userDTO.getPhoneNumber())) {
            throw new ApplicationException(Message.User.INVALID_PHONE_NUMBER);
        }
        if (userDTO.getAddress().length() >= ADDRESS_LENGTH) {
            throw new ApplicationException(Message.User.ADDRESS_LENGTH);
        }
    }

    public static void validateChangePassword(UserDTO userDTO) throws ApplicationException, DBException {
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

    public static void validateAccountStatusUpdate(String userId, String accountStatus) throws ApplicationException, DBException {
        if (!validate.isPositiveInteger(userId)) {
            throw new ApplicationException(Message.Common.NOT_A_POSITIVE_INTEGER);
        }
        if (userServices.getUser(Integer.parseInt(userId)) == null) {
            throw new ApplicationException(Message.User.NO_SUCH_USER);
        }
        if (accountStatus == null || accountStatus.isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!AccountStatus.isAccountStatus(accountStatus)) {
            throw new ApplicationException(Message.Order.NOT_A_ORDER_STATUS);
        }
    }
}
