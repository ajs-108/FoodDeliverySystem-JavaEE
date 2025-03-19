package common;

public class Message {

    public class Error {
        public final static String INTERNAL_ERROR = "Oop! Something went wrong.";
    }

    public class User {
        public final static String MANDATORY = "Field is Mandatory";
        public final static String NAME_LENGTH = "Name should be equal to or less than 30 characters.";
        public final static String EMAIL_LENGTH = "Email should be equal to or less than 255 Characters";
        public final static String INVALID_EMAIL = "Please enter valid email address";
        public final static String EMAIL_EXISTS = "The entered email address already exists, " +
                                                "please use another email address.";
        public final static String INVALID_PASSWORD = "Please enter valid Password. It should be between 8-40 " +
                                                    "characters and should be a combination of uppercase and " +
                                                    "lowercase letters, numbers, and special characters";
        public final static String INVALID_PHONE_NUMBER = "Please enter valid Phone number. It should be combination " +
                                                        "of 10 numbers starting with 1-9 numbers.";
        public final static String PHONE_NUMBER_EXISTS = "The entered Phone number already exists, " +
                                                        "please use another phone number.";
        public final static String INVALID_USER = "There is no user by this email. Please SignUp first to access " +
                                                "the Application";
        public final static String INCORRECT_LOGIN_CREDENTIALS = "Login Credentials are incorrect.";
    }
}
