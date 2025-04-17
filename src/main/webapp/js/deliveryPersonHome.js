import {LOGOUT} from './constant/config.js';

document.getElementById('confirmLogout').addEventListener('click', confirmLogout);

async function confirmLogout() {
    if (confirm("Are you sure you want to log out?")) {
        try {
            const response = await fetch(`${LOGOUT}`, {
                method: "POST",
                credentials: "include"
            });

            if (response.ok) {
                window.location.href = "login.html";  // Redirect to login page after logout
            } else {
                alert("Logout failed. Please try again.");
            }
        } catch (error) {
            alert("Network error: " + error.message);
        }
    }
}