import {SIGNUP_DELIVERY_PERSON} from './constant/config.js';


fetch('sideBar.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('sidebar-container').innerHTML = data;
    });
document.getElementById("signupForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const userData = {
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value,
        phoneNumber: document.getElementById("phoneNumber").value,
        address: document.getElementById("address").value,
    };


    try {
        const response = await fetch(`${SIGNUP_DELIVERY_PERSON}`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(userData),
        });

        const result = await response.json();
        const responseMessage = document.getElementById("responseMessage");

        if (response.ok) {
            responseMessage.textContent = result.message || "Sign-up successful!";
            responseMessage.classList.add("success");
            responseMessage.style.display = "block";

            setTimeout(() => {
                window.location.href = "adminDashboard.html";
            }, 2000);
        } else {
            responseMessage.textContent = "Error: " + result.message;
            responseMessage.classList.add("error");
            responseMessage.style.display = "block";
        }
    } catch (error) {
        document.getElementById("responseMessage").textContent = "Network error!";
        document.getElementById("responseMessage").classList.add("error");
        document.getElementById("responseMessage").style.display = "block";
    }
});
