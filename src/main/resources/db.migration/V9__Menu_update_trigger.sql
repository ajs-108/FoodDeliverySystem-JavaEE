DROP TRIGGER IF EXISTS update_menu;

DELIMITER //
CREATE TRIGGER update_menu
    AFTER UPDATE ON food_item
    FOR EACH ROW
BEGIN
    UPDATE menu
        JOIN food_item ON menu.food_item_id = food_item.food_item_id
    SET menu.food_name = food_item.food_name, menu.food_description = food_item.food_description,
        menu.price = food_item.price, menu.discount = food_item.discount,
        menu.is_available = food_item.is_available, menu.category_id = food_item.category_id,
        menu.image_path = food_item.image_path, menu.rating = food_item.rating;
END//
DELIMITER ;