import { UPDATE_FOOD_ITEM, GET_FOOD_ITEM_BY_ID } from './constant/config.js';

// Fetch the food item details when the page loads
async function fetchFoodItem(foodItemId) {
    try {
        const response = await fetch(`${GET_FOOD_ITEM_BY_ID}?foodItemId=${foodItemId}`);
        const result = await response.json();

        if (!result.data || result.data.length === 0) {
            document.getElementById("error-msg").innerText = "Food item not found.";
            return;
        }

        const foodItem = result.data[0];

        // Save original data for update usage
        sessionStorage.setItem("originalFoodItemId", foodItem.foodItemId);
        sessionStorage.setItem("originalImagePath", foodItem.imagePath);
        sessionStorage.setItem("originalCategoryId", foodItem.category.categoryId);
        sessionStorage.setItem("originalCategoryName", foodItem.category.categoryName);

        // Pre-fill form fields
        document.getElementById("foodName").value = foodItem.foodName;
        document.getElementById("foodDescription").value = foodItem.foodDescription;
        document.getElementById("category").value = foodItem.category.categoryName;
        document.getElementById("price").value = foodItem.price;
        document.getElementById("discount").value = foodItem.discount;
        document.getElementById("rating").value = foodItem.rating;
        document.getElementById("available").value = foodItem.available.toString();

        if (foodItem.imagePath) {
            const existingImage = document.getElementById("existing-image");
            existingImage.src = foodItem.imagePath.replace(/\\/g, "/");
            existingImage.style.display = "block";
        }

    } catch (error) {
        document.getElementById("error-msg").innerText = "Error fetching food item.";
        console.error(error);
    }
}

// Handle update form submit
document.getElementById("update-form").addEventListener("submit", async function (event) {
    event.preventDefault();

    const foodItemId = sessionStorage.getItem("originalFoodItemId");
    const imagePath = sessionStorage.getItem("originalImagePath");
    const categoryId = sessionStorage.getItem("originalCategoryId");
    const categoryName = sessionStorage.getItem("originalCategoryName");

    if (!foodItemId || !categoryId || !categoryName) {
        document.getElementById("error-msg").innerText = "Missing necessary data.";
        return;
    }

    // Prepare the JSON object for foodItem (key must be `foodItems` as per backend)
    const foodItem = {
        foodItemId: parseInt(foodItemId),
        foodName: document.getElementById("foodName").value.trim(),
        foodDescription: document.getElementById("foodDescription").value.trim(),
        price: parseFloat(document.getElementById("price").value),
        discount: parseFloat(document.getElementById("discount").value),
        imagePath: imagePath, // Send existing path if no image is updated
        category: {
            categoryId: parseInt(categoryId),
            categoryName: categoryName
        }
    };

    const formData = new FormData();
    formData.append("foodItem", new Blob([JSON.stringify(foodItem)], { type: "application/json" }));

    const imageFile = document.getElementById("image").files[0];
    if (imageFile) {
        formData.append("image", imageFile); // Send new image file if chosen
    }

    try {
        const response = await fetch(`${UPDATE_FOOD_ITEM}`, {
            method: "PUT",
            body: formData
        });

        const result = await response.json();
        console.log("Response from server:", result);

        if (result.message === "Food Item information is updated successfully") {
            alert("Food item updated successfully!");
            window.location.href = "adminDashboard.html";
        } else {
            document.getElementById("error-msg").innerText = result.message || "Failed to update food item.";
        }
    } catch (error) {
        document.getElementById("error-msg").innerText = "Error updating food item.";
        console.error("Update error:", error);
    }
});

// On page load, trigger fetch
document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const foodItemId = urlParams.get("foodItemId");

    if (foodItemId) {
        fetchFoodItem(foodItemId);
    } else {
        document.getElementById("error-msg").innerText = "Food item ID is missing from URL.";
    }
});
