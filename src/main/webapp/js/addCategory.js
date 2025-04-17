import {ADD_CATEGORY} from './constant/config.js';


document.getElementById('add-btn').addEventListener('click', addCategoryInput);
document.getElementById('submit-btn').addEventListener('click', submitCategories);

function addCategoryInput() {
    const categoryList = document.getElementById('category-list');
    const div = document.createElement('div');
    div.classList.add('category-input');
    div.innerHTML = '<input type="text" placeholder="Enter category name" class="category-name"> <button onclick="removeCategory(this)">X</button>';
    categoryList.appendChild(div);
}

function removeCategory(button) {
    button.parentElement.remove();
}

async function submitCategories() {
    const inputs = document.querySelectorAll('.category-name');
    const categories = [];
    inputs.forEach(input => {
        if (input.value.trim() !== '') {
            categories.push({categoryName: input.value.trim()});
        }
    });

    if (categories.length === 0) {
        document.getElementById('message').textContent = 'Please add at least one category';
        return;
    }

    try {
        const response = await fetch(`${ADD_CATEGORY}`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(categories)
        });
        if (response.ok) {
            document.getElementById('message').textContent = 'Categories added successfully!';
            setTimeout(() => {
                window.location.href = "adminDashboard.html"; // Redirect to admin dashboard
            }, 1500);
        } else {
            document.getElementById('message').textContent = 'Failed to add categories';
        }
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('message').textContent = 'Error adding categories';
    }
}
