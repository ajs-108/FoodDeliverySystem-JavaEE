// import {
//     GET_DELIVERY_PERSONS,
//     GET_ALL_ORDERS,
//     ASSIGN_DELIVERY_PERSON,
//     GET_ORDER_STATUS,
//     UPDATE_ORDER_STATUS
// } from './constant/config.js';
//
// let orderStatuses = [];
// let deliveryPartners = [];
//
// fetch('sideBar.html')
//     .then(response => response.text())
//     .then(data => {
//         document.getElementById('sidebar-container').innerHTML = data;
//     });
//
// document.addEventListener("DOMContentLoaded", async function () {
//     await fetchDeliveryPartners();
//     await fetchOrders();
//     setupDropdownListeners();
// });
//
// async function fetchDeliveryPartners() {
//     try {
//         const res = await fetch(`${GET_DELIVERY_PERSONS}`);
//         const data = await res.json();
//         deliveryPartners = data.data || [];
//     } catch (e) {
//         console.error("Error fetching delivery partners:", e);
//     }
// }
//
// async function fetchOrderStatuses() {
//     try {
//         const res = await fetch(GET_ORDER_STATUS);
//         const data = await res.json();
//         orderStatuses = data.data || [];
//     } catch (e) {
//         console.error("Error fetching order statuses:", e);
//     }
// }
//
// async function fetchOrders() {
//     const loader = document.getElementById("loader");
//     const errorMessage = document.getElementById("error-message");
//     const ordersTable = document.getElementById("ordersTable");
//     const ordersBody = document.getElementById("ordersBody");
//
//     try {
//         const res = await fetch(`${GET_ALL_ORDERS}`);
//         const result = await res.json();
//
//         loader.style.display = "none";
//
//         if (res.ok && result.data && result.data.length > 0) {
//             ordersTable.style.display = "table";
//             ordersBody.innerHTML = "";
//
//             result.data.forEach(order => {
//                 const tr = document.createElement("tr");
//
//                 const orderDate = new Date(...order.orderDateTime);
//                 const formattedDate = orderDate.toLocaleString();
//
//                 const items = order.orderFoodItems.map(item =>
//                     `${item.foodItemDTO.foodName} (x${item.quantity})`
//                 ).join(", ");
//
//                 let buttonHTML = '';
//
//                 if (!order.deliveryPerson || Object.keys(order.deliveryPerson).length === 0) {
//                     buttonHTML = `<button class="assign-btn not-assigned" id="assign-btn-${order.orderId}" onclick="openAssignModal(${order.orderId})">Assign</button>`;
//                 } else {
//                     buttonHTML = `<button class="assign-btn assigned-btn" disabled>Assigned</button>`;
//                 }
//
//                 const statusDropdown = createStatusDropdown(order.orderStatus, order.orderId);
//
//                 const tdStatus = document.createElement('td');
//                 tdStatus.appendChild(statusDropdown); // Correctly append the dropdown element
//
//                 tr.innerHTML = `
//                     <td>${order.orderId}</td>
//                     <td>${order.user.firstName} ${order.user.lastName}</td>
//                     <td>₹${order.totalPrice.toFixed(2)}</td>
//                     <td>${order.paymentStatus}</td>
//                     <td>${formattedDate}</td>
//                     <td>${items}</td>
//                     <td>${buttonHTML}</td>
//                 `;
//
//                 tr.appendChild(tdStatus); // Append the status cell with dropdown to the row
//                 ordersBody.appendChild(tr);
//             });
//         } else {
//             errorMessage.style.display = "block";
//             errorMessage.textContent = "No orders found.";
//         }
//     } catch (e) {
//         loader.style.display = "none";
//         errorMessage.style.display = "block";
//         errorMessage.textContent = "Failed to fetch orders.";
//         console.error(e);
//     }
// }
//
// window.openAssignModal = function (orderId) {
//     const modal = document.getElementById("assignModal");
//     const select = document.getElementById("deliveryPartnerSelect");
//     const assignBtn = document.getElementById("assignDeliveryPartner");
//
//     // Reset and populate dropdown
//     select.innerHTML = `<option value="">-- Select Delivery Partner --</option>`;
//     deliveryPartners.forEach(partner => {
//         const option = document.createElement("option");
//         option.value = partner.userId;
//         option.textContent = `${partner.firstName} ${partner.lastName}`;
//         select.appendChild(option);
//     });
//
//     // Set modal state
//     modal.setAttribute("data-order-id", orderId);
//     modal.style.display = "flex";
//     assignBtn.disabled = true;
//     select.focus();
// };
//
// // Listen for changes on select dropdown to enable/disable button
// function setupDropdownListeners() {
//     document.getElementById("deliveryPartnerSelect").addEventListener("change", function () {
//         const assignBtn = document.getElementById("assignDeliveryPartner");
//         assignBtn.disabled = this.value === "";
//     });
//
//     // Assign delivery partner click
//     document.getElementById('assignDeliveryPartner').addEventListener('click', assignDeliveryPartner);
// }
//
// async function assignDeliveryPartner() {
//     const modal = document.getElementById("assignModal");
//     const orderId = modal.getAttribute("data-order-id");
//     const partnerId = document.getElementById("deliveryPartnerSelect").value;
//
//     try {
//         const res = await fetch(`${ASSIGN_DELIVERY_PERSON}?orderId=${orderId}&deliveryPersonId=${partnerId}`, {
//             method: "PUT"
//         });
//
//         const result = await res.json();
//
//         if (res.ok && result.message === "Delivery Person Assigned for Order") {
//             alert("Delivery person assigned successfully!");
//             const btn = document.getElementById(`assign-btn-${orderId}`);
//             btn.classList.add("assigned-btn");
//             btn.disabled = true;
//             btn.textContent = "Assigned";
//             modal.style.display = "none";
//             await fetchOrders();
//         } else {
//             alert("Failed to assign delivery person.");
//         }
//     } catch (e) {
//         console.error("Error assigning delivery person:", e);
//         alert("Error assigning delivery person.");
//     }
// }
//
// function createStatusDropdown(currentStatus, orderId) {
//     const select = document.createElement('select');
//     select.classList.add('status-toggle');
//
//     // Populate the dropdown with available statuses
//     orderStatuses.forEach(status => {
//         const option = document.createElement('option');
//         option.value = status;
//         option.textContent = status;
//         if (status === currentStatus) {
//             option.selected = true;
//         }
//         select.appendChild(option);
//     });
//
//     // Add event listener for status change
//     select.addEventListener('change', function () {
//         updateOrderStatus(orderId, select.value);
//     });
//
//     return select;
// }
//
// async function updateOrderStatus(orderId, newStatus) {
//     try {
//         const res = await fetch(`${UPDATE_ORDER_STATUS}?orderId=${orderId}&status=${newStatus}`, {
//             method: "PUT",
//         });
//
//         const result = await res.json();
//
//         if (res.ok) {
//             alert("Order status updated successfully!");
//         } else {
//             alert("Failed to update order status.");
//         }
//     } catch (e) {
//         console.error("Error updating order status:", e);
//         alert("Error updating order status.");
//     }
// }
//
// // Close modal on outside click
// window.onclick = function (event) {
//     const modal = document.getElementById("assignModal");
//     if (event.target === modal) {
//         modal.style.display = "none";
//     }
// };
import {
    GET_DELIVERY_PERSONS,
    GET_ALL_ORDERS,
    ASSIGN_DELIVERY_PERSON,
    GET_ORDER_STATUS,
    UPDATE_ORDER_STATUS
} from './constant/config.js';

let orderStatuses = [];
let deliveryPartners = [];

fetch('sideBar.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('sidebar-container').innerHTML = data;
    });

document.addEventListener("DOMContentLoaded", async function () {
    await fetchOrderStatuses(); // Fetch the order statuses before rendering orders
    await fetchDeliveryPartners();
    await fetchOrders();
    setupDropdownListeners();
});

// Fetch the order statuses from the backend
async function fetchOrderStatuses() {
    try {
        const res = await fetch(GET_ORDER_STATUS);
        const data = await res.json();
        orderStatuses = data.data || []; // Assign to global orderStatuses
    } catch (e) {
        console.error("Error fetching order statuses:", e);
    }
}

async function fetchDeliveryPartners() {
    try {
        const res = await fetch(`${GET_DELIVERY_PERSONS}`);
        const data = await res.json();
        deliveryPartners = data.data || [];
    } catch (e) {
        console.error("Error fetching delivery partners:", e);
    }
}

async function fetchOrders() {
    const loader = document.getElementById("loader");
    const errorMessage = document.getElementById("error-message");
    const ordersTable = document.getElementById("ordersTable");
    const ordersBody = document.getElementById("ordersBody");

    try {
        const res = await fetch(`${GET_ALL_ORDERS}`);
        const result = await res.json();

        loader.style.display = "none";

        if (res.ok && result.data && result.data.length > 0) {
            ordersTable.style.display = "table";
            ordersBody.innerHTML = "";

            result.data.forEach(order => {
                const tr = document.createElement("tr");

                const orderDate = new Date(...order.orderDateTime);
                const formattedDate = orderDate.toLocaleString();

                const items = order.orderFoodItems.map(item =>
                    `${item.foodItemDTO.foodName} (x${item.quantity})`
                ).join(", ");

                let buttonHTML = '';

                if (!order.deliveryPerson || Object.keys(order.deliveryPerson).length === 0) {
                    buttonHTML = `<button class="assign-btn not-assigned" id="assign-btn-${order.orderId}" onclick="openAssignModal(${order.orderId})">Assign</button>`;
                } else {
                    buttonHTML = `<button class="assign-btn assigned-btn" disabled>Assigned</button>`;
                }

                const statusDropdown = createStatusDropdown(order.orderStatus, order.orderId);

                const tdStatus = document.createElement('td');
                tdStatus.appendChild(statusDropdown); // Correctly append the dropdown element

                tr.innerHTML = `
                    <td>${order.orderId}</td>
                    <td>${order.user.firstName} ${order.user.lastName}</td>
                    <td>₹${order.totalPrice.toFixed(2)}</td>
                    <td>${order.paymentStatus}</td>
                    <td>${formattedDate}</td>
                    <td>${items}</td>
                    <td>${buttonHTML}</td>
                `;

                tr.appendChild(tdStatus); // Append the status cell with dropdown to the row
                ordersBody.appendChild(tr);
            });
        } else {
            errorMessage.style.display = "block";
            errorMessage.textContent = "No orders found.";
        }
    } catch (e) {
        loader.style.display = "none";
        errorMessage.style.display = "block";
        errorMessage.textContent = "Failed to fetch orders.";
        console.error(e);
    }
}

window.openAssignModal = function (orderId) {
    const modal = document.getElementById("assignModal");
    const select = document.getElementById("deliveryPartnerSelect");
    const assignBtn = document.getElementById("assignDeliveryPartner");

    // Reset and populate dropdown
    select.innerHTML = `<option value="">-- Select Delivery Partner --</option>`;
    deliveryPartners.forEach(partner => {
        const option = document.createElement("option");
        option.value = partner.userId;
        option.textContent = `${partner.firstName} ${partner.lastName}`;
        select.appendChild(option);
    });

    // Set modal state
    modal.setAttribute("data-order-id", orderId);
    modal.style.display = "flex";
    assignBtn.disabled = true;
    select.focus();
};

// Listen for changes on select dropdown to enable/disable button
function setupDropdownListeners() {
    document.getElementById("deliveryPartnerSelect").addEventListener("change", function () {
        const assignBtn = document.getElementById("assignDeliveryPartner");
        assignBtn.disabled = this.value === "";
    });

    // Assign delivery partner click
    document.getElementById('assignDeliveryPartner').addEventListener('click', assignDeliveryPartner);
}

async function assignDeliveryPartner() {
    const modal = document.getElementById("assignModal");
    const orderId = modal.getAttribute("data-order-id");
    const partnerId = document.getElementById("deliveryPartnerSelect").value;

    try {
        const res = await fetch(`${ASSIGN_DELIVERY_PERSON}?orderId=${orderId}&deliveryPersonId=${partnerId}`, {
            method: "PUT"
        });

        const result = await res.json();

        if (res.ok && result.message === "Delivery Person Assigned for Order") {
            alert("Delivery person assigned successfully!");
            const btn = document.getElementById(`assign-btn-${orderId}`);
            btn.classList.add("assigned-btn");
            btn.disabled = true;
            btn.textContent = "Assigned";
            modal.style.display = "none";
            await fetchOrders();
        } else {
            alert("Failed to assign delivery person.");
        }
    } catch (e) {
        console.error("Error assigning delivery person:", e);
        alert("Error assigning delivery person.");
    }
}

function createStatusDropdown(currentStatus, orderId) {
    const select = document.createElement('select');
    select.classList.add('status-toggle');

    // Populate the dropdown with available statuses
    if (orderStatuses.length > 0) {
        orderStatuses.forEach(status => {
            const option = document.createElement('option');
            option.value = status;
            option.textContent = status;
            if (status === currentStatus) {
                option.selected = true;
            }
            select.appendChild(option);
        });
    }

    // Add event listener for status change
    select.addEventListener('change', function () {
        updateOrderStatus(orderId, select.value);
    });

    return select;
}

async function updateOrderStatus(orderId, newStatus) {
    try {
        const res = await fetch(`${UPDATE_ORDER_STATUS}?orderId=${orderId}&orderStatus=${newStatus}`, {
            method: "PUT",
        });

        const result = await res.json();

        if (res.ok) {
            alert("Order status updated successfully!");
        } else {
            alert("Failed to update order status.");
        }
    } catch (e) {
        console.error("Error updating order status:", e);
        alert("Error updating order status.");
    }
}

// Close modal on outside click
window.onclick = function (event) {
    const modal = document.getElementById("assignModal");
    if (event.target === modal) {
        modal.style.display = "none";
    }
};
