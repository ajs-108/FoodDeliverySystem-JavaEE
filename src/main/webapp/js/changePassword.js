import {CHANGE_PASSWORD} from './constant/config.js';


document.getElementById("changePasswordForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const newPassword = document.getElementById("newPassword").value;
    const responseMessage = document.getElementById("responseMessage");

    try {
        const response = await fetch(`${CHANGE_PASSWORD}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({email, password, newPassword}),
            credentials: "include"
        });

        const data = await response.json();

        if (response.ok) {
            responseMessage.textContent = data.message || "Password changed successfully!";
            setTimeout(() => {
                window.location.href = "adminDashboard.html"; // Redirect to admin dashboard
            }, 1500);
            responseMessage.style.color = "green";
        } else {
            responseMessage.textContent = "Error: " + (data.message || response.statusText);
            responseMessage.style.color = "red";
        }
    } catch (error) {
        responseMessage.textContent = "Network error: " + error.message;
        responseMessage.style.color = "red";
    }
});
