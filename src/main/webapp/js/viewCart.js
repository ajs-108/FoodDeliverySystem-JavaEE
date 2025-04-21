import { PLACE_ORDER, GET_CART, REMOVE_FROM_CART, UPDATE_CART_ITEM_QUANTITY, BASE_IMG_PATH } from './constant/config.js';

document.getElementById("checkoutBtn").addEventListener("click", async function () {
    try {
        const response = await fetch(`${PLACE_ORDER}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                orderStatus: "ORDER_RECEIVED",
                paymentStatus: "PAID"
            })
        });

        const result = await response.json();

        if (response.ok) {
            alert(result.message);
            setTimeout(() => window.location.href = "orderConfirmed.html", 1000);
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
            const { foodItemDTO, quantity } = item;
            const itemPrice = foodItemDTO.price * quantity;
            total += itemPrice;

            const isAvailable = true; // You can set logic here if needed

            const cartItem = document.createElement("div");
            cartItem.classList.add("cart-item");
            cartItem.id = `item-${foodItemDTO.foodItemId}`;
            cartItem.innerHTML = `
                <img src="${BASE_IMG_PATH}${foodItemDTO.imagePath}" alt="${foodItemDTO.foodName}">
                <div class="item-info">
                    <span>${foodItemDTO.foodName}</span>
                    ${foodItemDTO.price === 0 ? `<p class="free-item">Free Item</p>` : ''}
                </div>
                <div id="quantity-container-${foodItemDTO.foodItemId}" class="quantity-container">
                    <button class="minus-btn">-</button>
                    <span id="quantity-${foodItemDTO.foodItemId}">${quantity}</span>
                    <button class="plus-btn">+</button>
                </div>
                <span id="price-${foodItemDTO.foodItemId}">₹${itemPrice.toFixed(2)}</span>
            `;

            // Optional styling based on availability
            cartItem.style.backgroundColor = isAvailable ? "#e6ffe6" : "#ffe6e6";
            if (!isAvailable) {
                cartItem.classList.add("unavailable");
            }

            // Add event listeners
            if (isAvailable) {
                const minusBtn = cartItem.querySelector('.minus-btn');
                const plusBtn = cartItem.querySelector('.plus-btn');

                minusBtn.addEventListener('click', () => updateQuantity(foodItemDTO.foodItemId, -1));
                plusBtn.addEventListener('click', () => updateQuantity(foodItemDTO.foodItemId, 1));
            }

            cartContainer.appendChild(cartItem);
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

async function updateQuantity(foodItemId, change) {
    const quantityElem = document.getElementById(`quantity-${foodItemId}`);
    let quantity = parseInt(quantityElem.textContent);

    if (quantity + change >= 1) {
        quantity += change;
        quantityElem.textContent = quantity;
        await updateCartAPI(foodItemId, quantity);
        fetchCart(); // Refresh after update
    } else {
        await removeFromCart(foodItemId);
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
                foodItemDTO: { foodItemId: foodItemId },
                quantity: quantity
            }
        ]
    };

    await fetch(`${UPDATE_CART_ITEM_QUANTITY}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });
}

fetchCart();
