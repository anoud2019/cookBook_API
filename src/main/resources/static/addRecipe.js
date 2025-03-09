document.getElementById('addRecipeForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const recipeDTO = {
        name: document.getElementById('name').value,
        instructions: document.getElementById('instructions').value,
        ingredients: document.getElementById('ingredients').value.split(',').map(ing => ({ name: ing.trim() }))
    };

    const formData = new FormData();
    formData.append('dto', new Blob([JSON.stringify(recipeDTO)], { type: 'application/json' }));
    formData.append('file', document.getElementById('image').files[0]);

    fetch('http://localhost:8080/recipes/add', {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token') // إضافة التوكن هنا
        },
        body: formData,
    })
        .then(response => {
            if (!response.ok) {
                return Promise.reject(new Error('Network response was not ok'));
            }
            return response.json();
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
            console.error('Error:', error);
            const messageDiv = document.getElementById('message');
            if (messageDiv) {
                messageDiv.textContent = 'Error adding recipe. Please try again.';
                messageDiv.style.color = 'red';
            } else {
                alert('Error adding recipe. Please try again.');
            }
        });
});