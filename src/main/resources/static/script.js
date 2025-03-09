document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('token');

    try {
        if (token) {
            const payload = JSON.parse(atob(token.split('.')[1]));
            const role = payload.role;

            if (role === 'ADMIN') {
                document.querySelectorAll('.admin-only').forEach(button => {
                    button.style.display = 'inline-block';
                });
                document.querySelector('.add-button').style.display = 'inline-block';
            } else {
                document.querySelectorAll('.admin-only').forEach(button => {
                    button.style.display = 'none';
                });
                document.querySelector('.add-button').style.display = 'none';
            }

            document.getElementById('authLinks').style.display = 'none';
            document.getElementById('logoutButton').style.display = 'inline-block';

            localStorage.setItem('userRole', role);
        } else {
            document.getElementById('authLinks').style.display = 'block';
            document.getElementById('logoutButton').style.display = 'none';
            document.querySelector('.add-button').style.display = 'none';
        }
    } catch (e) {
        console.error('Error decoding token:', e);
    }
    document.getElementById('results').style.display = 'none';

    fetchAllRecipes();
});


function redirectToSignIn() {
    alert("You need to sign in first.");
    window.location.href = "signin.html";
}

function logout() {
    localStorage.removeItem('token');
    window.location.href = "signin.html";
}

function fetchAllRecipes() {
    const headers = {};
    const token = localStorage.getItem('token');

    if (token) {
        headers['Authorization'] = 'Bearer ' + token;
    }

    fetch('http://localhost:8080/recipes/getAll', {
        method: 'GET',
        headers: headers
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (!Array.isArray(data)) {
                console.error("Unexpected data format:", data);
                return;
            }
            displayAllRecipes(data);
        })
        .catch(error => console.error('Error fetching recipes:', error));
}

function displayAllRecipes(recipes) {
    const allRecipesDiv = document.getElementById('allRecipes');
    const userRole = localStorage.getItem('userRole'); // Retrieve user role from localStorage
    if (!allRecipesDiv) {
        console.error("Element with id 'allRecipes' not found in the DOM.");
        return;
    }
    allRecipesDiv.innerHTML = '';
    if (recipes.length === 0) {
        allRecipesDiv.innerHTML = '<p>No recipes available.</p>';
        return;
    }

    recipes.forEach(recipe => {
        const recipeCard = document.createElement('div');
        recipeCard.className = 'recipe-card';

        // Add click event to open modal
        recipeCard.onclick = () => openModal(recipe);

        const imageHtml = recipe.imageUrl
            ? `<img src="${recipe.imageUrl}" alt="${recipe.name}" style="width:100%; height:auto;">`
            : '<p>No image available</p>';

        // Add buttons with the class "admin-only" conditionally
        let cardActions = '';
        if (userRole === 'ADMIN') {
            cardActions = `
                <div class="card-actions">
                    <!-- زر التعديل مع أيقونة القلم -->
                    <button onclick="event.stopPropagation(); editRecipe(${recipe.id})" class="btn edit-btn admin-only">
                        <i class="fas fa-pencil-alt"></i> Edit
                    </button>
        
                    <!-- زر الحذف مع أيقونة السلة -->
                    <button onclick="event.stopPropagation(); deleteRecipe(${recipe.id})" class="btn delete-btn admin-only">
                        <i class="fas fa-trash-alt"></i> Delete
                    </button>
                </div>
            `;
        }

        recipeCard.innerHTML = `
            <h3>${recipe.name}</h3>
            ${imageHtml}
            
            ${cardActions}
        `;
        allRecipesDiv.appendChild(recipeCard);
    });
}


function openModal(recipe) {
    const token = localStorage.getItem('token');
    const messageArea = document.getElementById('messageArea');
    const chatMessages = document.getElementById('chatMessages');

    if (!token) {
        messageArea.style.backgroundColor = '#fce8e6';
        messageArea.style.color = '#d32f2f';
        messageArea.innerHTML = 'You need to sign in to view recipe details. <a href="signin.html" onclick="redirectToSignInFromMessage(); return false;">Sign In</a> or <a href="signup.html" onclick="redirectToSignUpFromMessage(); return false;">Sign Up</a>';
        messageArea.style.display = 'block';
        chatMessages.innerHTML = ''; 
        return;
    }

    messageArea.style.display = 'none';
    chatMessages.innerHTML = '';

    const modal = document.getElementById('recipeModal');
    if (!modal) {
        console.error("Modal element not found.");
        return;
    }

    // Populate the modal with recipe data
    document.getElementById('modalRecipeTitle').innerText = recipe.name;
    document.getElementById('modalRecipeIngredients').innerHTML = `
        ${recipe.ingredients.map(ingredient => `<li>${ingredient.name}</li>`).join('')}
    `;
    document.getElementById('modalRecipeInstructions').innerText = recipe.instructions || 'No instructions available.';
    document.getElementById('modalRecipeImage').src = recipe.imageUrl || '';

    // Show the modal
    modal.style.display = 'block';

    // Close modal when clicking the close button or outside the modal
    document.getElementById('closeModalButton').onclick = () => {
        modal.style.display = 'none';
    };

    window.onclick = (event) => {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    };
}


function search() {
    const token = localStorage.getItem('token');
    const messageArea = document.getElementById('messageArea');
    const chatMessages = document.getElementById('chatMessages');

    if (!token) {
        messageArea.style.backgroundColor = '#e8f5fc';
        messageArea.style.color = '#1976d2';
        messageArea.innerHTML = 'You need to sign in to search for recipes. <a href="signin.html" onclick="redirectToSignInFromMessage(); return false;">Sign In</a> or <a href="signup.html" onclick="redirectToSignUpFromMessage(); return false;">Sign Up</a>';
        messageArea.style.display = 'block';
        chatMessages.innerHTML = ''; 
        return;
    }

    messageArea.style.display = 'none';
    chatMessages.innerHTML = '';  

    const query = document.getElementById('searchInput').value.trim();
    const allRecipesDiv = document.getElementById('allRecipes');
    const resultsDiv = document.getElementById('results');
    const searchResultsDiv = document.getElementById('searchResults');

    allRecipesDiv.style.display = 'none';
    resultsDiv.style.display = 'block';
    searchResultsDiv.innerHTML = '';

    let apiUrl;
    if (query.includes(',')) {
        const ingredients = query.split(',').map(ing => ing.trim()).join(',');
        apiUrl = `http://localhost:8080/recipes/searchRecipesByIngredients?ingredients=${encodeURIComponent(ingredients)}`;
    } else {
        apiUrl = `http://localhost:8080/recipes/searchRecipesByName?name=${encodeURIComponent(query)}`;
    }

    const headers = {
        'Authorization': 'Bearer ' + token
    };

    fetch(apiUrl, {
        method: 'GET',
        headers: headers
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(recipes => {
            if (!Array.isArray(recipes)) {
                console.error("Unexpected data format:", recipes);
                searchResultsDiv.innerHTML = "<p>Unexpected data format.</p>";
                return;
            }
            displaySearchResults(recipes);
            if (recipes.length > 0) { 
                document.getElementById('results').style.display = 'block';
            } else {
                document.getElementById('results').style.display = 'none';
            }

        })
        .catch(error => {
            console.error('Error searching:', error);
            searchResultsDiv.innerHTML = `<p>Error during search: ${error.message}</p>`;
        });
}

function redirectToSignInFromMessage() {
    window.location.href = "signin.html";
}

function redirectToSignUpFromMessage() {
    window.location.href = "signup.html";
}

function displaySearchResults(recipes) {
    const searchResultsDiv = document.getElementById('searchResults');
    const userRole = localStorage.getItem('userRole'); // Retrieve user role from localStorage
    if (!searchResultsDiv) {
        console.error("Search results section not found.");
        return;
    }

    searchResultsDiv.innerHTML = '';
    if (recipes.length === 0) {
        searchResultsDiv.innerHTML = '<p>No results found.</p>';
        return;
    }

    recipes.forEach(recipe => {
        const recipeCard = document.createElement('div');
        recipeCard.className = 'recipe-card';

        // Add click event to open modal
        recipeCard.onclick = () => openModal(recipe);

        const imageHtml = recipe.imageUrl
            ? `<img src="${recipe.imageUrl}" alt="${recipe.name}" style="width:100%; height:auto;">`
            : '<p>No image available</p>';

        // Add buttons with the class "admin-only" conditionally
        let cardActions = '';
        if (userRole === 'ADMIN') {
            cardActions = `
                <div class="card-actions">
                   
                    <button onclick="event.stopPropagation(); editRecipe(${recipe.id})" class="btn edit-btn admin-only">
                        <i class="fas fa-pencil-alt"></i> Edit
                    </button>
        
                    
                    <button onclick="event.stopPropagation(); deleteRecipe(${recipe.id})" class="btn delete-btn admin-only">
                        <i class="fas fa-trash-alt"></i> Delete
                    </button>
                </div>
            `;
        }

        recipeCard.innerHTML = `
            <h3>${recipe.name}</h3>
            ${imageHtml}
            
            ${cardActions}
        `;
        searchResultsDiv.appendChild(recipeCard);
    });
}

let recipeIdToDelete;

function deleteRecipe(recipeId) {
    recipeIdToDelete = recipeId;
    const confirmMessage = document.getElementById('deleteConfirmMessage');
    confirmMessage.style.display = 'block';
    setTimeout(() => {
        confirmMessage.style.opacity = '1';
    }, 10);
}

function confirmDelete(confirmed) {
    const confirmMessage = document.getElementById('deleteConfirmMessage');
    confirmMessage.style.opacity = '0';
    setTimeout(() => {
        confirmMessage.style.display = 'none';
        if (confirmed) {
            performDelete();
        }
    }, 500);
}

function performDelete() {
    fetch(`http://localhost:8080/recipes/delete/${recipeIdToDelete}`, {
        method: 'DELETE',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response;
        })
        .then(() => {
            showDeleteSuccess();
            fetchAllRecipes();
        })
        .catch(error => {
            console.error('Error deleting recipe:', error);
            alert('Error deleting recipe.');
        });
}

function showDeleteSuccess() {
    const successMessage = document.getElementById('deleteSuccessMessage');
    successMessage.style.display = 'block';
    setTimeout(() => {
        successMessage.style.opacity = '1';
    }, 10);

    setTimeout(() => {
        successMessage.style.opacity = '0';
        setTimeout(() => {
            successMessage.style.display = 'none';
        }, 500);
    }, 2000);
}

function editRecipe(recipeId) {
    window.location.href = `editRecipe.html?id=${recipeId}`;
}

function sendMessage() {
    const token = localStorage.getItem('token');
    const chatMessages = document.getElementById('chatMessages');
    const messageArea = document.getElementById('messageArea');

    if (!token) {
        chatMessages.innerHTML = `
            <div style="
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100%;
            ">
                <p style="
                    text-align: center;
                    font-size: 1.2em;
                    color: #ff9800;
                    background-color: #fff3e0;
                    padding: 10px;
                    border-radius: 5px;
                ">
                    You need to sign in to use the chat.
                    <a href="signin.html" onclick="redirectToSignInFromMessage(); return false;">Sign In</a>
                    or
                    <a href="signup.html" onclick="redirectToSignUpFromMessage(); return false;">Sign Up</a>
                </p>
            </div>
        `;
        messageArea.style.display = 'none'; 
        return;
    }

    const chatInput = document.getElementById('chatInput');
    const message = chatInput.value.trim();

    if (message === '') {
        alert('Please enter a message.');
        return;
    }

    chatMessages.innerHTML += `<p><strong>You:</strong> ${message}</p>`;
    fetch(`http://localhost:8080/ai/${encodeURIComponent(message)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.text();
        })
        .then(data => {
            chatMessages.innerHTML += `<p><strong>AI:</strong> ${data}</p>`;
            chatInput.value = '';
            chatMessages.scrollTop = chatMessages.scrollHeight;
        })
        .catch(error => {
            console.error('Error:', error);
            chatMessages.innerHTML += `<p><strong>AI:</strong> Error: ${error.message}</p>`;
        });
}