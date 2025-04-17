import {GET_USER} from './constant/config.js';


document.addEventListener("DOMContentLoaded", async function () {
    try {
        const response = await fetch(`${GET_USER}`, {
            method: "GET",
            credentials: "include"
        });

        if (!response.ok) throw new Error("Failed to fetch user details");

        const userData = await response.json();
        console.log("🚀 Full API Response:", userData);

        // Extract correct user data
        let user = userData.data;  // Since data is wrapped inside "data"

        console.log("🛠 Extracted User Data:", user);

        // Populate form fields with existing user data
        document.getElementById("firstName").value = user.firstName || "";
        document.getElementById("lastName").value = user.lastName || "";
        document.getElementById("phoneNumber").value = user.phoneNumber || "";
        document.getElementById("address").value = user.address || "";

    } catch (error) {
        console.error("❌ Error fetching user data:", error);
    }
});


document.getElementById("editProfileForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const userData = {
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        phoneNumber: document.getElementById("phoneNumber").value,
        address: document.getElementById("address").value
    };

    const responseMessage = document.getElementById("responseMessage");

    try {
        const response = await fetch(`${GET_USER}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(userData),
            credentials: "include" // Ensures session data is included
        });

        const data = await response.json();

        if (response.ok) {
            responseMessage.textContent = data.message;
            responseMessage.style.color = "green";
            setTimeout(() => {
                window.location.href = "adminDashboard.html";  // Change to the actual dashboard page URL
            }, 2000);  // Redirect after 2 seconds
        } else {
            responseMessage.textContent = "Error updating profile: " + (data.message);
            responseMessage.style.color = "red";
        }
    } catch (error) {
        responseMessage.textContent = "Network error: " + error.message;
        responseMessage.style.color = "red";
    }

    responseMessage.style.display = "block";
});
