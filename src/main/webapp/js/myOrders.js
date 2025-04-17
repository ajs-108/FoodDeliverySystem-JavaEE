import { GET_ALL_ORDERS_OF_USER } from "./constant/config.js";

document.addEventListener("DOMContentLoaded", () => {
    fetchMyOrders();
});

async function fetchMyOrders() {
    const container = document.getElementById("orders-container");

    try {
        const res = await fetch(GET_ALL_ORDERS_OF_USER);
        const result = await res.json();

        if (res.ok && result.data && result.data.length > 0) {
            result.data.forEach(order => {
                const orderCard = document.createElement("div");
                orderCard.classList.add("order-card");

                const orderDate = new Date(...order.orderDateTime).toLocaleString();
                const status = order.orderStatus.toUpperCase();

                let statusClass = "";
                if (status === "DELIVERED") {
                    statusClass = "status-delivered";
                } else if (status === "CANCELLED") {
                    statusClass = "status-cancelled";
                }

                const header = `
                    <div class="order-header">
                        <div><strong>Order #${order.orderId}</strong><br><small>${orderDate}</small></div>
                        <button class="status-button ${statusClass}">${status}</button>
                    </div>
                `;

                const foodItems = order.orderFoodItems.map(item => `
                    <div class="food-item">
                        ${item.foodItemDTO.foodName} (x${item.quantity})
                    </div>
                `).join("");

                const reviewButton = (status === "DELIVERED")
                    ? `<button class="review-btn" onclick="location.href='giveReview.html?orderId=${order.orderId}'">Give Review</button>`
                    : "";

                orderCard.innerHTML = header + foodItems + reviewButton;

                container.appendChild(orderCard);
            });
        } else {
            container.innerHTML = "<p>No orders found.</p>";
        }
    } catch (e) {
        console.error("Error fetching orders:", e);
        container.innerHTML = "<p>Something went wrong while loading orders.</p>";
    }
}
