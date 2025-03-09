document.addEventListener('DOMContentLoaded', function () {
    const urlParams = new URLSearchParams(window.location.search);
    const recipeId = urlParams.get('id');
    // Validate recipeId
    if (!recipeId || !/^\d+$/.test(recipeId)) {
        alert('Invalid or missing Recipe ID.');
        window.location.href = '/recipes.html';
        return;
    }

    function fetchWithAuth(url, options = {}) {
        const token = localStorage.getItem('token');
        if (!token) {
            alert('You must log in to access this page.');
            window.location.href = '/login.html';
            return Promise.reject('No token found');
        }
        const headers = {
            ...options.headers,
            'Authorization': `Bearer ${token}`
        };
        return fetch(url, { ...options, headers })
            .then(response => {
                if (response.status === 401) {
                    alert('Session expired, please log in again.');
                    localStorage.removeItem('token');
                    window.location.href = '/login.html';
                    return Promise.reject('Unauthorized');
                }
                if (!response.ok) {
                    return response.text().then(text => {
                        throw new Error(`HTTP error! Status: ${response.status}, Message: ${text || 'No message provided'}`);
                    });
                }
                return response.json();
            });
    }

    document.getElementById('editRecipeForm').addEventListener('submit', function (e) {
        e.preventDefault();

        // Create a recipeDTO object with only the filled fields
        const recipeDTO = {
            id: recipeId
        };

        const name = document.getElementById('name').value.trim();
        if (name) {
            recipeDTO.name = name;
        }

        const instructions = document.getElementById('instructions').value.trim();
        if (instructions) {
            recipeDTO.instructions = instructions;
        }

        const ingredients = document.getElementById('ingredients').value.trim();
        if (ingredients) {
            recipeDTO.ingredients = ingredients.split(',').map(ing => {
                return { name: ing.trim() };
            });
        }

        const formData = new FormData();
        formData.append('dto', new Blob([JSON.stringify(recipeDTO)], { type: 'application/json' }));

        const imageInput = document.getElementById('image');
        if (imageInput.files.length > 0) {
            formData.append('file', imageInput.files[0]);
        }

        fetchWithAuth(`http://localhost:8080/recipes/update/${recipeId}`, {
            method: 'PUT',
            body: formData
        })
            .then(data => {
                const successMessage = document.getElementById('successMessage');
                successMessage.style.display = 'block';
                setTimeout(() => {
                    successMessage.style.opacity = '1';
                }, 10);

                setTimeout(() => {
                    successMessage.style.opacity = '0';
                    setTimeout(() => {
                        successMessage.style.display = 'none';
                        window.location.href = 'index.html';
                    }, 500);
                }, 2000);
            })
            .catch(error => {
                console.error('Error updating recipe:', error);
                document.getElementById('message').textContent = 'Error updating recipe.';
                document.getElementById('message').classList.remove('success-message');
                document.getElementById('message').classList.add('error-message');
            });
    });
});