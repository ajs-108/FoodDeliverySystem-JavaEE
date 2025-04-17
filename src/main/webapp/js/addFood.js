import {ADD_FOOD_ITEM, GET_CATEGORIES} from './constant/config.js';


async function loadCategories() {
    try {

        const response = await fetch(`${GET_CATEGORIES}`);
        const result = await response.json();

        const categories = result.data || [];

        if (!Array.isArray(categories)) {
            throw new Error("Invalid category format");
        }

        const categoryDropdown = document.getElementById('category');
        categoryDropdown.innerHTML = '<option value="">Select Category</option>';

        categories.forEach(category => {
            const option = document.createElement('option');
            option.value = category.categoryId;
            option.textContent = category.categoryName;
            categoryDropdown.appendChild(option);
        });
    } catch (error) {
        console.error('Error fetching categories:', error);
        document.getElementById('category').innerHTML = '<option value="">Failed to load categories</option>';
    }
}

document.getElementById('submitFoodItem').addEventListener('click', submitFoodItem);

async function submitFoodItem() {
    const foodName = document.getElementById('foodName').value.trim();
    const foodDescription = document.getElementById('foodDescription').value.trim();
    const price = parseFloat(document.getElementById('price').value);
    const discount = parseFloat(document.getElementById('discount').value);
    const categoryId = document.getElementById('category').value;
    const foodImage = document.getElementById('foodImage').files[0];

    if (!foodName || !foodDescription || isNaN(price) || isNaN(discount) || !categoryId || !foodImage) {
        document.getElementById('message').textContent = 'Please fill all fields and select an image';
        return;
    }

    const foodItem = {
        foodName,
        foodDescription,
        price,
        discount,
        category: {
            categoryId: parseInt(categoryId),
        },
        available: true
    };

    const formData = new FormData();
    formData.append("foodItem", new Blob([JSON.stringify(foodItem)], {type: "application/json"}));
    formData.append("image", foodImage);

    try {

        const response = await fetch(`${ADD_FOOD_ITEM}`, {
            method: 'POST',
            body: formData
        });

        const result = await response.json();

        if (response.ok) {
            document.getElementById('message').textContent = 'Food item added successfully!';
            setTimeout(() => {
                window.location.href = "adminDashboard.html"; // Redirect to admin dashboard
            }, 1500);
        } else {
            document.getElementById('message').textContent = `Failed: ${result.message || "Something went wrong"}`;
        }
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('message').textContent = 'Error adding food item';
    }
}

// Load categories on page load
document.addEventListener("DOMContentLoaded", () => {
    console.log("DOM fully loaded"); // check this in browser console
    loadCategories();
});