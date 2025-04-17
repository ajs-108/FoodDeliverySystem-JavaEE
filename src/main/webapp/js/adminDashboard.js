import {GET_FOOD_ITEMS, UPDATE_FOOD_ITEM_AVAILABILITY, REMOVE_FOOD_ITEM, LOGOUT} from './constant/config.js';


fetch('sideBar.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('sidebar-container').innerHTML = data;
    });


async function fetchFoodItems() {
    try {
        const response = await fetch(`${GET_FOOD_ITEMS}`);
        const result = await response.json();

        const foodItems = result.data || [];

        if (!Array.isArray(foodItems)) {
            throw new Error("Invalid food items format");
        }

        const foodTable = document.getElementById('food-items-table');

        // Clear old rows except the header
        foodTable.innerHTML = `
            <tr>
                <th>Food Image</th>
                <th>Food Name</th>
                <th>Description</th>
                <th>Category</th>
                <th>Price</th>
                <th>Discount</th>
                <th>Rating</th>
                <th>Availability</th>
                <th>Edit</th>
                                <th>Delete</th>

            </tr>
        `;

        foodItems.forEach(food => {
            const row = document.createElement('tr');

            row.innerHTML = `

               <td>

    <img src="${food.imagePath}" alt="${food.foodName}" style="width: 50px; height: 50px; border-radius: 5px;">
</td>

                <td>${food.foodName}</td>
                <td>${food.foodDescription}</td>
                <td>${food.category.categoryName}</td>
                <td>₹${food.price.toFixed(2)}</td>
                <td>${food.discount}%</td>
                <td>
                     ${getStars(food.rating)}
                </td>


    <td>
   <button class="toggle-btn ${food.available ? 'On' : 'Off'}">
    ${food.available ? "Available" : "Unavailable"}
</button>
</td>

                <td>
                   <button class="update-btn">Edit</button>
                </td>


 <td>
<button class="delete-btn">Delete</button>                </td>
                    `;
            foodTable.appendChild(row);

            row.querySelector('.toggle-btn').addEventListener('click', () => {
                toggleAvailability(food.foodItemId, food.available);
            });

            row.querySelector('.delete-btn').addEventListener('click', () => {
                deleteFood(food.foodItemId);
            });

            row.querySelector('.update-btn').addEventListener('click', () => {
                updateFood(food.foodItemId);
            });
        });
    } catch (error) {
        console.error('Error fetching food items:', error);
        document.getElementById('food-items-table').innerHTML = `
            <tr><td colspan="6">Failed to load food items.</td></tr>
        `;
    }
}

function updateFood(foodItemId) {
    window.location.href = `updateFood.html?foodItemId=${foodItemId}`;
}

async function deleteFood(foodItemId) {
    try {
        const response = await fetch(`${REMOVE_FOOD_ITEM}?foodItemId=${foodItemId}`, {
            method: 'DELETE',
        });

        if (response.ok) {
            alert("Food Item Deleted successfully!");
            window.location.href = 'adminDashboard.html';
        } else {
            alert("Failed to delete food item. Please try again.");
            console.error("Failed to delete food item.");
        }
    } catch (error) {
        alert("An error occurred while deleting the food item.");
        console.error("Error:", error);
    }
}

async function toggleAvailability(foodItemId, currentAvailability) {
    const newAvailability = !currentAvailability;

    try {
        const response = await fetch(`${UPDATE_FOOD_ITEM_AVAILABILITY}?foodItemId=${foodItemId}&available=${newAvailability}`, {
            method: 'PUT',
        });

        if (response.ok) {
            const button = document.querySelector(`#food-item-${foodItemId} .toggle-btn`);
            setTimeout(() => {
                window.location.reload(); // 🔄 Refresh the page
            }, 200);
            if (!button) {
                console.error(`Button not found for food item ID ${foodItemId}`);
                return;
            }

            if (newAvailability) {
                button.textContent = "Available";
                button.classList.remove('Off');
                button.classList.add('On');
                button.style.backgroundColor = "green";
            } else {
                button.textContent = "Unavailable";
                button.classList.remove('On');
                button.classList.add('Off');
                button.style.backgroundColor = "red";
            }
            setTimeout(() => {
                window.location.href = "adminDashboard.js";
            }, 1000);
        } else {
            console.error("Failed to update availability");
        }
    } catch (error) {
        console.error("Error updating availability:", error);
    }
}


function getStars(rating) {
    let stars = '';
    for (let i = 1; i <= 5; i++) {
        if (i <= rating) {
            stars += '⭐'; // Filled star
        } else {
            stars += '☆'; // Empty star
        }
    }
    return stars;
}

function loadNotificationPage() {
    window.location.href = "notification.html";
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


document.getElementById('toggleDropdown').addEventListener('click', toggleDropdown);

function toggleDropdown() {
    const dropdown = document.getElementById("profile-dropdown");
    dropdown.style.display = dropdown.style.display === "block" ? "none" : "block";
}

fetchFoodItems();
