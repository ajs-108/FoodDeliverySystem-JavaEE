// Base URL
export const BASE_URL = "http://localhost:8080/food_delivery_app_war_exploded";

// Authentication API URLs
export const SIGNUP_CUSTOMER = `${BASE_URL}/customerSignUp`;
export const SIGNUP_DELIVERY_PERSON = `${BASE_URL}/deliveryPersonSignUp`;
export const LOGIN = `${BASE_URL}/loginServlet`;
export const LOGOUT = `${BASE_URL}/logoutServlet`;


//image path
export const BASE_IMG_PATH="http://localhost:8080/food_delivery_app_war_exploded/";
// User APIs
export const GET_USER = `${BASE_URL}/user`;
export const UPDATE_USER = `${BASE_URL}/user`;

// Delivery Person APIs
export const GET_DELIVERY_PERSONS = `${BASE_URL}/deliveryPersons`;

// Customer APIs
export const GET_CUSTOMERS = `${BASE_URL}/customers`;

// Food Item Category APIs
export const ADD_CATEGORY = `${BASE_URL}/addCategory`;
export const GET_CATEGORIES = `${BASE_URL}/categories`;

// Food Item APIs
export const ADD_FOOD_ITEM = `${BASE_URL}/addFoodItem`;
export const GET_FOOD_ITEMS = `${BASE_URL}/foodItems`;
export const GET_FOOD_ITEM_BY_ID = `${BASE_URL}/foodItems`;
export const UPDATE_FOOD_ITEM = `${BASE_URL}/updateFoodItem`;
export const UPDATE_FOOD_ITEM_AVAILABILITY = `${BASE_URL}/updateAvailability`;
export const REMOVE_FOOD_ITEM = `${BASE_URL}/removeFoodItem`;

// Shopping Cart APIs
export const ADD_TO_CART = `${BASE_URL}/addToCart`;
export const GET_CART = `${BASE_URL}/getCart`;
export const UPDATE_CART_ITEM_QUANTITY = `${BASE_URL}/updateQuantity`;
export const REMOVE_FROM_CART = `${BASE_URL}/removeFromCart`;

// Order APIs
export const PLACE_ORDER = `${BASE_URL}/placeOrder`;
export const GET_ORDER_BY_ID = `${BASE_URL}/getOrderById`;
export const GET_ALL_ORDERS = `${BASE_URL}/getAllOrders`;
export const GET_ORDER_STATUS = `${BASE_URL}/getAllOrderStatus`;
export const UPDATE_ORDER_STATUS = `${BASE_URL}/updateOrderStatus`;
export const ASSIGN_DELIVERY_PERSON = `${BASE_URL}/assignDeliveryPerson`;
export const GET_RECENT_ORDER = `${BASE_URL}/getRecentOrderOfUser`;
export const GET_ALL_ORDERS_OF_USER = `${BASE_URL}/getAllOrdersOfUser`;

// Password Change
export const CHANGE_PASSWORD = `${BASE_URL}/changePassword`;

//Review
export const POST_REVIEW = `${BASE_URL}/postReview`;
export const GET_REVIEW_FOR_USER=`${BASE_URL}/getAllReviewOfUser`;
export const GET_REVIEW_BY_ID = `${BASE_URL}/getReview?reviewId=`;
export const GET_ALL_REVIEWS=`${BASE_URL}/getAllReview;`


