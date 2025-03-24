insert into user_role
values
    (1001, 'ROLE_SUPER_ADMIN'),
    (1002, 'ROLE_CUSTOMER'),
    (1003, 'ROLE_DELIVERY_PERSON');

insert into user_(first_name, last_name, email, password_, phone_number, role_id)
values
    ('Admin', 'restaurant', 'admin@email.com', 'Admin@123', '985xxxxxxx', 1001);
