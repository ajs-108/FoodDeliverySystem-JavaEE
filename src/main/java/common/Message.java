package common;

public class Message {

    public static class Error {
        public static final String GENERIC_ERROR = "Oop! Something went wrong.";
        public static final String UNAUTHORIZED_ACCESS = "You are unauthorized to access this, Please login first";
        public static final String ACCESS_DENIED = "Access denied, you do not have authority to access this";
    }

    public static class Common {
        public static final String MANDATORY = "Field is Mandatory";
    }

    public static class User {
        public static final String LOGIN = "Login Successful";
        public static final String LOGOUT = "Logout Successful";
        public static final String NAME_LENGTH = "Name should be equal to or less than 30 characters.";
        public static final String EMAIL_LENGTH = "Email should be equal to or less than 255 Characters";
        public static final String INVALID_EMAIL = "The Email you entered is invalid";
        public static final String EMAIL_EXISTS = "The entered email address is already registered, " +
                                                "please use another email address.";
        public static final String INVALID_PASSWORD = "The password you entered is invalid. Please enter valid one, " +
                                                    "it should be between 8-16 characters and should be a " +
                                                    "combination of uppercase and lowercase letters, numbers, and " +
                                                    "special characters";
        public static final String INVALID_PHONE_NUMBER = "The phone number you entered is invalid.";
        public static final String PHONE_NUMBER_EXISTS = "The entered Phone number is already registered, " +
                                                        "please enter another phone number.";
        public static final String ADDRESS_LENGTH = "Address should be less than or equal to 255 characters.";
        public static final String NO_SUCH_USER = "There is no user registered by this email. Please register first to access " +
                                                "the application";
        public static final String INCORRECT_LOGIN_CREDENTIALS = "Login Credentials are incorrect.";
        public static final String USER_REGISTERED = "User Registered successfully";
        public static final String DELIVERY_PERSON_REGISTERED = "Delivery Person Registered successfully";
        public static final String USER_INFO_UPDATES = "User Information is updated successfully";
    }

    public static class FoodItem {
        public static final String FOOD_NAME_LENGTH = "Name should be less than or equal to 30 characters";
        public static final String FOOD_DESCRIPTION_LENGTH = "Description should be less than or equal to 255 characters";
        public static final String PRICE = "Value should be Decimal/Integer number";
    }
}
