create database food_delivery_db;

use food_delivery_db;

create table user_role (
    role_id int not null,
    user_role varchar(40) not null,
    PRIMARY KEY(role_id)
);

create table user_ (
    user_id int auto_increment not null,
    first_name varchar(30) not null,
    last_name varchar(30) not null,
    email varchar(255) not null unique,
    password_ varchar(41) not null,
    phone_number varchar(14) not null unique check(length(phone_number) = 10),
    address varchar(255),
    created_on timestamp not null default current_timestamp,
    update_on timestamp on update current_timestamp,
    role_id int not null,
    PRIMARY KEY(user_id),
    FOREIGN KEY(role_id) references user_role(role_id)
);

create table category (
    category_id int not null auto_increment,
    category_name varchar(40) not null,
    PRIMARY KEY(category_id)
);

create table food_item (
    food_item_id INT auto_increment NOT NULL,
    food_name VARCHAR(30) NOT NULL,
    food_description VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    discount DOUBLE DEFAULT 0,
    is_available BOOLEAN NOT NULL DEFAULT(TRUE),
    category_id INT NOT NULL,
    created_on TIMESTAMP NOT NULL default current_timestamp,
    updated_on TIMESTAMP NULL on update current_timestamp,
    image_path VARCHAR(255) not null,
    rating DOUBLE NOT NULL DEFAULT(1),
    PRIMARY KEY(food_item_id),
    FOREIGN KEY(category_id) references category(category_id)
);

create table shopping_cart (
    food_item_id int not null,
    user_id int not null,
    quantity smallint not null,
    FOREIGN KEY(food_item_id) references food_item(food_item_id),
    FOREIGN KEY(user_id) references user_(user_id)
);

create table order_ (
    order_id INT auto_increment NOT NULL,
    user_id	INT	NOT NULL,
    delivery_person_id INT NULL,
    total_price	DOUBLE	NOT NULL,
    order_status ENUM(
        'Order Received',
        'Preparing',
        'Not Assigned',
        'Assigned',
        'Ready for Delivery',
        'Out for Delivery',
        'Delivered',
        'Cancelled') NOT NULL,
    order_date_time	TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    payment_status ENUM('Not Paid', 'Paid') NULL,
    PRIMARY KEY(order_id),
    FOREIGN KEY(user_id) references user_(user_id),
    FOREIGN KEY(delivery_person_id) references user_(user_id)
);

create table order_food_items (
    food_item_id int not null,
    order_id int not null,
    quantity smallint not null,
    FOREIGN KEY(food_item_id) references food_item(food_item_id),
    FOREIGN KEY(order_id) references order_(order_id)
);

create table review_rating_table (
    review_id INT auto_increment NOT NULL,
    user_id	INT NOT NULL,
    food_item_id INT NOT NULL,
    order_id INT NOT NULL,
    rating TINYINT NULL,
    review VARCHAR(250) NULL,
    PRIMARY KEY (review_id),
    FOREIGN KEY(user_id) references user_(user_id),
    FOREIGN KEY(food_item_id) references food_item(food_item_id),
    FOREIGN KEY(order_id) references order_(order_id)
);