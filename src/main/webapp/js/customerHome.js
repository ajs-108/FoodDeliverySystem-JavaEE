import {
    GET_FOOD_ITEMS,
    GET_CART,
    REMOVE_FROM_CART,
    UPDATE_CART_ITEM_QUANTITY,
    ADD_TO_CART,
    LOGOUT,
    BASE_IMG_PATH
} from './constant/config.js';

let allFoodItems = [];

async function fetchFoodItems() {
    try {
        const response = await fetch(`${GET_FOOD_ITEMS}`);
        const data = await response.json();
        allFoodItems = data.data;
        displayFoodItems(allFoodItems);
        await loadExistingCartItems();
    } catch (error) {
        console.error('Error fetching food items:', error);
    }
}

function displayFoodItems(items) {
    const container = document.getElementById('foodContainer');
    container.innerHTML = '';
    items.forEach(item => {
        const isAvailable = item.available; // Check availability from backend

        const card = document.createElement('div');
        card.classList.add('food-card');
        card.id = `food-${item.foodItemId}`;

        card.innerHTML = `
<img src="${BASE_IMG_PATH}${item.imagePath}" alt="${item.foodName}">
            <h3>${item.foodName}</h3>
            <p>${item.foodDescription}</p>
            <p class="price">₹${item.price.toFixed(2)}</p>
            <button class="add-to-cart" id="cart-btn-${item.foodItemId}" ${!isAvailable ? 'disabled' : ''}>
                ${isAvailable ? 'Add to Cart' : 'Unavailable'}
            </button>
            <div id="quantity-container-${item.foodItemId}" class="quantity-container" style="display: none;">
              <button class="minus-btn">-</button>
              <span id="quantity-${item.foodItemId}">1</span>
              <button class="plus-btn">+</button>
            </div>
            <button class="remove-btn" id="remove-btn-${item.foodItemId}" style="display: none;">Remove</button>
            <button class="view-cart" id="view-cart-${item.foodItemId}" style="display: none;">View Cart</button>
        `;

        // Apply visual styling based on availability
        card.style.backgroundColor = isAvailable ? "#e6ffe6" : "#ffe6e6"; // Green if available, red if not
        if (!isAvailable) {
            card.classList.add("unavailable");
        }

        container.appendChild(card);

        // Only add event listeners if available
        if (isAvailable) {
            card.querySelector('.add-to-cart').addEventListener('click', () => addToCart(item.foodItemId));
            card.querySelector('.minus-btn').addEventListener('click', () => updateQuantity(item.foodItemId, -1));
            card.querySelector('.plus-btn').addEventListener('click', () => updateQuantity(item.foodItemId, 1));
            card.querySelector('.remove-btn').addEventListener('click', () => removeFromCart(item.foodItemId));
            card.querySelector('.view-cart').addEventListener('click', () => viewCart());
        }
    });
}

async function loadExistingCartItems() {
    try {
        const res = await fetch(`${GET_CART}`);
        const data = await res.json();

        if (data?.data?.cartFoodItemsDTOList?.length) {
            data.data.cartFoodItemsDTOList.forEach(item => {
                const foodItemId = item.foodItemDTO.foodItemId;
                const quantity = item.quantity;

                document.getElementById(`cart-btn-${foodItemId}`).style.display = "none";
                document.getElementById(`quantity-container-${foodItemId}`).style.display = "flex";
                document.getElementById(`remove-btn-${foodItemId}`).style.display = "block";
                document.getElementById(`view-cart-${foodItemId}`).style.display = "block";
                document.getElementById(`quantity-${foodItemId}`).textContent = quantity;
            });
        }
    } catch (error) {
        console.error('Error loading existing cart items:', error);
    }
}

async function addToCart(foodItemId) {
    document.getElementById(`cart-btn-${foodItemId}`).style.display = "none";
    document.getElementById(`quantity-container-${foodItemId}`).style.display = "flex";
    document.getElementById(`remove-btn-${foodItemId}`).style.display = "block";
    document.getElementById(`view-cart-${foodItemId}`).style.display = "block";
    await addCartAPI(foodItemId, 1);
}

async function updateQuantity(foodItemId, change) {
    let quantityElem = document.getElementById(`quantity-${foodItemId}`);
    let quantity = parseInt(quantityElem.textContent);
    if (quantity + change >= 1) {
        quantity += change;
        quantityElem.textContent = quantity;
        await updateCartAPI(foodItemId, quantity);
    }
}

async function removeFromCart(foodItemId) {
    try {
        const response = await fetch(`${REMOVE_FROM_CART}?foodItemId=${foodItemId}`, {method: "DELETE"});
        const data = await response.json();
        if (data.message === "Food Item removed from cart") {
            document.getElementById(`cart-btn-${foodItemId}`).style.display = "block";
            document.getElementById(`quantity-container-${foodItemId}`).style.display = "none";
            document.getElementById(`remove-btn-${foodItemId}`).style.display = "none";
            document.getElementById(`view-cart-${foodItemId}`).style.display = "none";
        }
    } catch (error) {
        console.error("Error removing item:", error);
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

async function addCartAPI(foodItemId, quantity) {
    const payload = {
        cartFoodItemsDTOList: [
            {
                foodItemDTO: {foodItemId: foodItemId},
                quantity: quantity
            }
        ]
    };
    await fetch(`${ADD_TO_CART}`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(payload)
    });
}

function viewCart() {
    window.location.href = "viewCart.html";
}


function toggleDropdown() {
    const dropdown = document.getElementById("profile-dropdown");
    dropdown.style.display = dropdown.style.display === "flex" ? "none" : "flex";
}

function goToCart() {
    window.location.href = "viewCart.html";
}

function goToOrdersPage() {
    window.location.href = "myOrders.html";  // Make sure to create this page to display the orders
}

document.getElementById('confirmLogout').addEventListener('click', confirmLogout);

async function confirmLogout() {
    if (confirm("Are you sure you want to log out?")) {
        try {
            const response = await fetch(`${LOGOUT}`, {
                method: "POST",
                credentials: "include"
            });

            if (response.ok) {
                window.location.href = "login.html";  // Redirect to login page after logout
            } else {
                alert("Logout failed. Please try again.");
            }
        } catch (error) {
            alert("Network error: " + error.message);
        }
    }
}

document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('toggleDropdown')?.addEventListener('click', toggleDropdown);
    document.getElementById('goToCart')?.addEventListener('click', goToCart);
    document.getElementById('goToOrdersPage')?.addEventListener('click', goToOrdersPage);

    fetchFoodItems(); // Only call this once DOM is ready
});
