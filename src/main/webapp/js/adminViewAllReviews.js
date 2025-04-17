import { BASE_IMG_PATH, GET_ALL_REVIEWS, GET_REVIEW_BY_ID } from './constant/config.js';

document.addEventListener("DOMContentLoaded", fetchReviews);

async function fetchReviews() {
    try {
        const res = await fetch(`${GET_ALL_REVIEWS}`);
        const result = await res.json();
        const container = document.getElementById("reviewsContainer");

        if (result.data && result.data.length > 0) {
            result.data.forEach((review) => {
                container.appendChild(createReviewCard(review));
            });
        } else {
            container.innerHTML = "<p style='color:#888;'>No reviews available.</p>";
        }
    } catch (error) {
        console.error("Error fetching reviews:", error);
        document.getElementById("reviewsContainer").innerHTML = "<p>Error loading reviews.</p>";
    }
}

function createReviewCard(review) {
    const card = document.createElement("div");
    card.className = "review-card";

    const img = document.createElement("img");
    img.src = `${BASE_IMG_PATH}` + review.foodItemDTO.imagePath.replace(/\\/g, "/");
    img.alt = review.foodItemDTO.foodName;

    const details = document.createElement("div");
    details.className = "review-details";

    details.innerHTML = `
        <h3>${review.foodItemDTO.foodName}</h3>
        <p><strong>User:</strong> ${review.userDTO.firstName}</p>
        <p><strong>Order ID:</strong> ${review.orderId}</p>
        <p><strong>Review:</strong> ${review.review}</p>
        <div class="review-rating">Rating: ${"★".repeat(review.rating)}${"☆".repeat(5 - review.rating)}</div>
    `;

    const viewBtn = document.createElement("button");
    viewBtn.textContent = "View";
    viewBtn.className = "view-btn";
    viewBtn.addEventListener("click", () => {
        window.location.href = `viewReview.html?reviewId=${review.reviewId}`;
    });

    card.appendChild(img);
    card.appendChild(details);
    card.appendChild(viewBtn);

    return card;
}
