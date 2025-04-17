import { BASE_IMG_PATH, GET_REVIEW_BY_ID } from './constant/config.js';

document.addEventListener("DOMContentLoaded", async () => {
    const params = new URLSearchParams(window.location.search);
    const reviewId = params.get("reviewId");

    if (!reviewId) {
        document.getElementById("reviewContainer").innerHTML = "<p>Review ID not found in URL.</p>";
        return;
    }

    try {
        const res = await fetch(`${GET_REVIEW_BY_ID}${reviewId}`);
        const result = await res.json();

        if (result.data) {
            const r = result.data;
            const container = document.getElementById("reviewContainer");

            container.innerHTML = `
                <img src="${BASE_IMG_PATH}${r.foodItemDTO.imagePath.replace(/\\/g, "/")}" alt="${r.foodItemDTO.foodName}">
                <div class="review-info">
                    <h2>${r.foodItemDTO.foodName}</h2>
                    <p><strong>User:</strong> ${r.userDTO.firstName}</p>
                    <p><strong>Order ID:</strong> ${r.orderId}</p>
                    <p><strong>Review:</strong> ${r.review}</p>
                    <div class="review-rating">${"★".repeat(r.rating)}${"☆".repeat(5 - r.rating)}</div>
                    <button class="back-btn" onclick="history.back()">⬅ Back</button>
                </div>
            `;
        } else {
            document.getElementById("reviewContainer").innerHTML = "<p>Review not found.</p>";
        }
    } catch (err) {
        console.error("Error fetching review:", err);
        document.getElementById("reviewContainer").innerHTML = "<p>Error loading review.</p>";
    }
});
