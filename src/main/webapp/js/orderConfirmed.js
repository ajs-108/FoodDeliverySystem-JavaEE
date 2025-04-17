import { GET_RECENT_ORDER } from './constant/config.js';



document.addEventListener("DOMContentLoaded", async function () {
    try {
    const res = await fetch(`${GET_RECENT_ORDER}`);
    const result = await res.json();

    const order = result.data;

    if (order) {
    document.getElementById("order-id").textContent = `#FD${String(order.orderId).padStart(8, '0')}`;
    document.getElementById("total-amount").textContent = `₹${order.totalPrice.toFixed(2)}`;
}
} catch (error) {
    console.error("Failed to fetch recent order:", error);
}
});
