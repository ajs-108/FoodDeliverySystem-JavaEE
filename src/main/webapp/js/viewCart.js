import {PLACE_ORDER, GET_CART, REMOVE_FROM_CART, UPDATE_CART_ITEM_QUANTITY, BASE_IMG_PATH} from './constant/config.js';



document.getElementById("checkoutBtn").addEventListener("click", async function () {
        try {
            const response = await fetch(`${PLACE_ORDER}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    orderStatus: "ORDER_RECEIVED",
                    paymentStatus: "PAID"
                })
            });

            const result = await response.json();

            if (response.ok) {
                alert(result.message); // shows "Order Placed"
                setTimeout(() => window.location.href = "orderConfirmed.html", 1000);
                // Optionally redirect or clear cart
            } else {
                alert("Failed to place order.");
            }
        } catch (error) {
            console.error("Error placing order:", error);
            alert("Something went wrong.");
        }
    });


    async function fetchCart() {
        try {
            const response = await fetch(`${GET_CART}`);
            const data = await response.json();

            const cartContainer = document.getElementById('cartItems');
            const totalDisplay = document.getElementById("total");
            const discountDisplay = document.getElementById("discount");
            const totalPriceDisplay = document.getElementById("totalPrice");

            if (!data.data || data.data.cartFoodItemsDTOList.length === 0) {
                cartContainer.innerHTML = "<p>No items in cart.</p>";
                totalDisplay.innerText = "Total: ₹0";
                discountDisplay.innerText = "Discount: ₹0";
                totalPriceDisplay.innerText = "Final Price: ₹0";
                return;
            }

            cartContainer.innerHTML = '';
            let total = 0, discount = 0, finalPrice = 0;

            data.data.cartFoodItemsDTOList.forEach((item) => {
                const {foodItemDTO, quantity} = item;
                const itemPrice = foodItemDTO.price * quantity;
                total += itemPrice;
                img.src = `${BASE_IMG_PATH}` + review.foodItemDTO.imagePath.replace(/\\/g, "/");

                cartContainer.innerHTML += `
                    <div class="cart-item" id="item-${foodItemDTO.foodItemId}">
                        <img src="http://localhost:8080/food_delivery_app_war_exploded/${foodItemDTO.imagePath}" alt="${foodItemDTO.foodName}">
                        <div class="item-info">
                            <span>${foodItemDTO.foodName}</span>
                            ${foodItemDTO.price === 0 ? `<p class="free-item">Free Item</p>` : ''}
                        </div>
                        <div class="quantity-controls">
                            <button onclick="updateQuantity(${foodItemDTO.foodItemId}, 'decrease', ${quantity})">-</button>
                            <span id="quantity-${foodItemDTO.foodItemId}">${quantity}</span>
                            <button onclick="updateQuantity(${foodItemDTO.foodItemId}, 'increase', ${quantity})">+</button>
                        </div>
                        <span id="price-${foodItemDTO.foodItemId}">₹${itemPrice.toFixed(2)}</span>
                    </div>
                `;
            });

            discount = total * 0.1;
            finalPrice = total - discount;

            totalDisplay.innerText = `Total: ₹${total.toFixed(2)}`;
            discountDisplay.innerText = `Discount: ₹${discount.toFixed(2)}`;
            totalPriceDisplay.innerText = `Final Price: ₹${finalPrice.toFixed(2)}`;
        } catch (error) {
            console.error("Error fetching cart:", error);
        }
    }

    async function updateQuantity(foodItemId, action, currentQuantity) {
        let newQuantity = currentQuantity;

        if (action === 'increase') {
            newQuantity++;
        } else if (action === 'decrease') {
            if (currentQuantity === 1) {
                await removeFromCart(foodItemId);
                return;
            }
            newQuantity--;
        }

        try {
            await updateCartAPI(foodItemId, newQuantity);
            fetchCart();
        } catch (error) {
            console.error("Error updating cart:", error);
        }
    }

    async function removeFromCart(foodItemId) {
        try {
            await fetch(`${REMOVE_FROM_CART}?foodItemId=${foodItemId}`, {
                method: "DELETE"
            });
            fetchCart();
        } catch (error) {
            console.error("Error removing item from cart:", error);
        }
    }

    async function updateCartAPI(foodItemId, quantity) {
        const payload = {
            cartFoodItemsDTOList: [
                {
                    foodItemDTO: {foodItemId: foodItemId},
                    quantity: quantity
                }
            ]
        };

        await fetch(`${UPDATE_CART_ITEM_QUANTITY}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(payload)
        });
    }

    fetchCart();
