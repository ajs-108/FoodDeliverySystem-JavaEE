// Import the constant at the top of the file, outside of any functions or event listeners
import {SIGNUP_CUSTOMER} from './constant/config.js';

document.addEventListener("DOMContentLoaded", function () {
    const signupForm = document.getElementById("signupForm");
    const responseMessage = document.getElementById("responseMessage");

    signupForm.addEventListener("submit", function (e) {
        e.preventDefault();

        let data = {
            firstName: document.getElementById("firstName").value.trim(),
            lastName: document.getElementById("lastName").value.trim(),
            email: document.getElementById("email").value.trim(),
            password: document.getElementById("password").value.trim(),
            phoneNumber: document.getElementById("phoneNumber").value.trim(),
            address: document.getElementById("address").value.trim(),
        };

        if (!data.firstName || !data.lastName || !data.email || !data.password || !data.phoneNumber || !data.address) {
            responseMessage.textContent = "All fields are required.";
            responseMessage.classList.remove("success");
            responseMessage.classList.add("error");
            responseMessage.style.display = "block";
            return;
        }

        fetch(SIGNUP_CUSTOMER, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(res => {
                alert(res.message || "Sign-up successful!"); // Native browser alert

                responseMessage.textContent = res.message || "Sign-up successful!";
                responseMessage.classList.remove("error");
                responseMessage.classList.add("success");
                responseMessage.style.display = "block";

                setTimeout(() => {
                    window.location.href = "login.html";
                }, 2000);
            })
            .catch(error => {
                let errorMessage = error.responseJSON?.message || "An error occurred during sign-up.";
                responseMessage.textContent = errorMessage;
                responseMessage.classList.remove("success");
                responseMessage.classList.add("error");
                responseMessage.style.display = "block";
            });
    });
});
