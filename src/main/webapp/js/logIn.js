import { LOGIN } from './constant/config.js';


document.getElementById("login-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Prevent default form submission

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const responseMessage = document.getElementById("response-message");

    // Reset message visibility
    responseMessage.style.display = "none";
    responseMessage.textContent = "";

    if (!email || !password) {
        responseMessage.textContent = "Please fill in all fields.";
        responseMessage.style.color = "red";
        responseMessage.style.display = "block";
        return;
    }

    const loginData = {email, password};
    try {
        const response = await fetch(`${LOGIN}`, {

            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(loginData)
        });

        const data = await response.json();
        console.log("API Response:", data); // Debugging

        if (!response.ok) {
            throw new Error(data.message || "Login failed. Please try again.");
        }

        const userRole = data.data?.role;
        if (!userRole) {
            throw new Error("Invalid response: No role found.");
        }

        // Success message
        responseMessage.textContent = "Login successful! Redirecting...";
        responseMessage.style.color = "green";
        responseMessage.style.display = "block";

        // Redirect based on role
        setTimeout(() => {
            switch (userRole) {
                case "ROLE_SUPER_ADMIN":
                    window.location.href = 'adminDashboard.html';
                    break;
                case "ROLE_CUSTOMER":
                    window.location.href = 'customerHome.html';
                    break;
                case "ROLE_DELIVERY_PERSON":
                    window.location.href = 'deliveryPersonHome.html';
                    break;
                default:
                    responseMessage.textContent = "Unauthorized access. Please contact support.";
                    responseMessage.style.color = "red";
            }
        }, 1500);
    } catch (error) {
        console.error("Login Error:", error);
        responseMessage.textContent = error.message;
        responseMessage.style.color = "red";
        responseMessage.style.display = "block";
    }
});
