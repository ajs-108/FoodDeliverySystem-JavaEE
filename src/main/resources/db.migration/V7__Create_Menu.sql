CREATE TABLE menu SELECT * FROM food_item;

ALTER TABLE menu ADD PRIMARY KEY (food_item_id);

DELIMITER //
CREATE TRIGGER insert_into_menu
    AFTER INSERT ON food_item
    FOR EACH ROW
BEGIN
    INSERT INTO menu (food_item_id, food_name, food_description, price, discount, category_id, image_path)
    values (new.food_item_id , new.food_name, new.food_description,
            new.price, new.discount, new.category_id, new.image_path);
END//
DELIMITER ;

DELIMITER //
CREATE TRIGGER update_menu
    AFTER UPDATE ON food_item
    FOR EACH ROW
BEGIN
    UPDATE menu
        JOIN food_item ON menu.food_item_id = food_item.food_item_id
    SET menu.food_name = food_item.food_name, menu.food_description = food_item.food_description,
        menu.price = food_item.price, menu.discount = food_item.discount,
        menu.category_id = food_item.category_id, menu.image_path = food_item.image_path;
END//
DELIMITER ;