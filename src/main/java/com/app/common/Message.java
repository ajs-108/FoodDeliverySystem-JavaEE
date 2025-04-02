package com.app.common;

public class Message {

    public static class Error {
        public static final String GENERIC_ERROR = "Oop! Something went wrong.";
        public static final String UNAUTHORIZED_ACCESS = "You are unauthorized to access this, Please login first";
        public static final String ACCESS_DENIED = "Access denied, you do not have authority to access this";
        public static final String INVALID_PARAMETER = "Not a query parameter recognizable by server";
    }

    public static class Common {
        public static final String MANDATORY = "Please fill out all required fields";
        public static final String ENTRY_ADDED = "Entry added Successfully";
        public static final String RESOURCE_NOT_AVAILABLE = "Requested resource is not available";
        public static final String NOT_A_POSITIVE_INTEGER = "Id's value should be a Positive Integer greater than 0";
        public static final String NOT_A_BOOLEAN = "Value should be either true or false";
    }

    public static class User {
        public static final String LOGIN = "Login Successful";
        public static final String LOGOUT = "Logout Successful";
        public static final String NAME_LENGTH = "Name should be equal to or less than 30 characters";
        public static final String EMAIL_LENGTH = "Email should be equal to or less than 255 Characters";
        public static final String INVALID_EMAIL = "The Email you entered is invalid";
        public static final String EMAIL_EXISTS = "The entered email address is already registered, " +
                                                "please use another email address";
        public static final String INVALID_PASSWORD = "The password you entered is invalid. Please enter valid one, " +
                                                    "it should be between 8-16 characters and should be a " +
                                                    "combination of uppercase and lowercase letters, numbers, and " +
                                                    "special characters";
        public static final String INVALID_PHONE_NUMBER = "The phone number you entered is invalid";
        public static final String PHONE_NUMBER_EXISTS = "The entered Phone number is already registered, " +
                                                        "please enter another phone number";
        public static final String ADDRESS_LENGTH = "Address should be less than or equal to 255 characters";
        public static final String NO_SUCH_USER = "There is no such user. Please register " +
                                                "first to access the application";
        public static final String NO_SUCH_DELIVERY_PERSON = "Delivery Person doesn't exists.";
        public static final String INCORRECT_LOGIN_CREDENTIALS = "Login Credentials are incorrect.";
        public static final String USER_REGISTERED = "User Registered successfully";
        public static final String DELIVERY_PERSON_REGISTERED = "Delivery Person Registered successfully";
        public static final String USER_INFO_UPDATED = "Your Information is updated successfully";
        public static final String INCORRECT_PASSWORD = "Please ensure current password is correct";
        public static final String SAME_PASSWORD = "Please choose a different password";
        public static final String PASSWORD_UPDATED = "Password updated successfully";
        public static final String NOT_A_DELIVERY_PERSON = "Not a Delivery Person";
    }

    public static class Category {
        public static final String CATEGORY_NAME_LENGTH = "Category name should be less than 40";
        public static final String CATEGORY_EXISTS = "Category you have entered already exists, Please enter " +
                                                    "another one.";
    }

    public static class FoodItem {
        public static final String FOOD_NAME_LENGTH = "Name should be less than or equal to 30 characters";
        public static final String FOOD_DESCRIPTION_LENGTH = "Description should be less than or equal to 255 characters";
        public static final String PRICE_DISCOUNT_DATATYPE = "Value should be Decimal/Integer number";
        public static final String FOOD_ITEM_UPDATED = "Food Item information is updated successfully";
        public static final String FOOD_ITEM_EXISTS = "Food Item you have entered already exists, Please enter " +
                                                    "another one";
    }

    public static class ShoppingCart {
        public static final String FOOD_ITEM_ADDED = "Food Item added to cart";
        public static final String FOOD_ITEM_REMOVED = "Food Item removed from cart";
        public static final String FOOD_ITEM_EXISTS = "Food Item already exists in cart";
        public static final String QUANTITY = "Quantity value should at least be 1";
        public static final String QUANTITY_VALUE = "Quantity value should less than or equal to 15";
        public static final String QUANTITY_UPDATED = "Food Item quantity updated";
    }

    public static class Order {
        public static final String PLACE_ORDER = "Order Placed";
        public static final String ORDER_STATUS = "Order status updated";
        public static final String NOT_A_ORDER_STATUS = "Invalid order status";
        public static final String NOT_A_PAYMENT_STATUS = "Invalid payment status";
        public static final String ORDER_DOES_NOT_EXISTS = "Order doesn't exists";
    }
}
