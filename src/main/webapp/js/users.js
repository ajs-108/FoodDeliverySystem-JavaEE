import {GET_CUSTOMERS, GET_RECENT_ORDER} from './constant/config.js';

// Load Sidebar
    fetch('sideBar.html')
    .then(response => response.text())
    .then(data => {
    document.getElementById('sidebar-container').innerHTML = data;
})
    .catch(error => console.error("Error loading sidebar: ", error));

    document.addEventListener("DOMContentLoaded", async function () {
    const loader = document.getElementById("loader");
    const errorMessage = document.getElementById("error-message");
    const deliveryTable = document.getElementById("delivery-table");
    const deliveryList = document.getElementById("delivery-list");

    try {
    // Fetch data from API
        const response = await fetch(`${GET_CUSTOMERS}`);
        const result = await response.json();

    loader.style.display = "none"; // Hide loader

    if (response.ok && Array.isArray(result.data) && result.data.length > 0) {
    deliveryTable.style.display = "table"; // Show table

    // Populate table with data
    result.data.forEach(person => {
    const row = document.createElement("tr");
    row.innerHTML = `
<td>${person.firstName + " " + person.lastName}</td>                            <td>${person.email}</td>
                            <td>${person.phoneNumber}</td>
                          <td>
        <button class="status-toggle ${
    (person.accountStatus || '').toUpperCase() === 'ACTIVATED' ? 'active' : 'inactive'
}">
            ${(person.accountStatus || '').toUpperCase() === 'ACTIVATED' ? 'Active' : 'Inactive'}
        </button>
    </td>
                        `;
    deliveryList.appendChild(row);
});
} else {
    errorMessage.textContent = "No delivery persons found.";
    errorMessage.style.display = "block";
}
} catch (error) {
    loader.style.display = "none"; // Hide loader
    errorMessage.textContent = "Error fetching data!";
    errorMessage.style.display = "block";
}
});
