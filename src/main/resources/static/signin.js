document.addEventListener('DOMContentLoaded', function () {
    const signInForm = document.getElementById('signInForm');
    const messageDiv = document.getElementById('message');

    if (signInForm) {
        signInForm.addEventListener('submit', function (event) {
            event.preventDefault();

            const userName = document.getElementById('userName').value;
            const password = document.getElementById('password').value;

            fetch('http://localhost:8080/auth/signin', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ userName, password })
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    localStorage.setItem('token', data.token);
                    showMessage('Login successful!', 'success');
                    setTimeout(() => {
                        window.location.href = 'index.html';
                    }, 1000);
                })
                .catch(error => {
                    console.error('Error during sign-in:', error);
                    showMessage('Invalid username or password.', 'error');
                });
        });
    }

    /**
     
     * @param {string} text 
     * @param {string} type 
     */
    function showMessage(text, type) {
        messageDiv.textContent = text;
        messageDiv.className = `message ${type}`;
        messageDiv.style.display = 'block';
        setTimeout(() => {
            messageDiv.style.display = 'none';
        }, 3000);
    }
});