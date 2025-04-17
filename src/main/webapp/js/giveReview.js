import { POST_REVIEW, GET_ORDER_BY_ID } from './constant/config.js';

document.addEventListener("DOMContentLoaded", () => {
    const urlParams = new URLSearchParams(window.location.search);
    const orderId = urlParams.get("orderId");

    if (orderId) {
        fetchOrderData(orderId);
    } else {
        document.getElementById('reviewForm').innerHTML = "<p>Missing order info.</p>";
    }
});

async function fetchOrderData(orderId) {
    try {
        const res = await fetch(`${GET_ORDER_BY_ID}?orderId=${orderId}`);
        const result = await res.json();

        if (res.ok && result.data) {
            renderReviewForm(result.data);
        } else {
            document.getElementById('reviewForm').innerHTML = "<p>Failed to load order details.</p>";
        }
    } catch (err) {
        console.error(err);
        document.getElementById('reviewForm').innerHTML = "<p>Error fetching order.</p>";
    }
}

function renderReviewForm(orderData) {
    const form = document.getElementById('reviewForm');
    form.innerHTML = '';

    const alreadyReviewed = orderData.reviewed === true;

    if (!orderData.orderFoodItems || orderData.orderFoodItems.length === 0) {
        form.innerHTML = "<p>No food items found in this order.</p>";
        return;
    }

    orderData.orderFoodItems.forEach((item, index) => {
        form.insertAdjacentHTML("beforeend", `
            <div class="review-card">
                <h4>${item.foodItemDTO.foodName}</h4>
                <label for="rating-${index}">Rating:</label>
                <select id="rating-${index}" ${alreadyReviewed ? "disabled" : ""}>
                    <option value="1">1 - Poor</option>
                    <option value="2">2 - Fair</option>
                    <option value="3">3 - Good</option>
                    <option value="4">4 - Very Good</option>
                    <option value="5" selected>5 - Excellent</option>
                </select>
                <label for="comment-${index}">Comment:</label>
                <textarea id="comment-${index}" rows="3" placeholder="Write your review..." ${alreadyReviewed ? "disabled" : ""}></textarea>
                <input type="hidden" id="foodId-${index}" value="${item.foodItemDTO.foodItemId}">
            </div>
        `);
    });

    if (alreadyReviewed) {
        form.insertAdjacentHTML("beforeend", `<button disabled style="background: gray;">Reviewed</button>`);
    } else {
        form.insertAdjacentHTML("beforeend", `<button type="submit">Submit Review</button>`);
        form.addEventListener("submit", (e) => {
            e.preventDefault();
            submitReview(orderData);
        });
    }
}

async function submitReview(orderData) {
    const reviewPayload = [];

    orderData.orderFoodItems.forEach((item, index) => {
        const rating = parseInt(document.getElementById(`rating-${index}`).value);
        const comment = document.getElementById(`comment-${index}`).value.trim();
        const foodItemId = document.getElementById(`foodId-${index}`).value;

        reviewPayload.push({
            userDTO: { userId: orderData.user.userId },
            foodItemDTO: { foodItemId },
            orderId: orderData.orderId,
            rating,
            review: comment
        });
    });

    try {
        const res = await fetch(POST_REVIEW, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(reviewPayload)
        });

        const result = await res.json();

        if (res.ok && result.message === "Review posted successfully") {
            alert("✅ Thank you for submitting your review!");
            window.location.href = "myOrders.html";
        } else if (result.message === "Review already exists for this order and food item") {
            alert("⚠️ You have already reviewed this order.");
            // Optional: Disable all inputs and button after alert
        } else {
            alert("❌ Failed to submit review. Please try again.");
        }
    } catch (error) {
        console.error('Error submitting reviews:', error);
        alert("❌ An error occurred while submitting the review.");
    }
}
