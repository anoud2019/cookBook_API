document.getElementById('signupForm').addEventListener('submit', function (event) {
    event.preventDefault();


    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const role = document.getElementById('role').value;
    const messageDiv = document.getElementById('message');

    fetch('http://localhost:8080/auth/signup', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userName: username, email, password, role })
    })
        .then(response => response.json())
        .then(data => {
            showMessage('Signup successful!', 'success');
            setTimeout(() => {
                window.location.href = 'signin.html';
            }, 1000);
        })
        .catch(error => {
            console.error('Error signing up:', error);
            showMessage('An error occurred while signing up. Please try again.', 'error');
        });

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