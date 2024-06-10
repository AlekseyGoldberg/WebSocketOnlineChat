
document.getElementById('loginBtn').addEventListener('click', function() {
    const username = document.getElementById('username').value;
    if (username) {
        window.location.href = `chat.html?username=${encodeURIComponent(username)}`;
    } else {
        alert('Please enter a username');
    }
});