  // Отправка сообщения
  sendBtn.addEventListener('click', function() {
    const message = messageInput.value;
    if (message) {
        const payload = JSON.stringify({ message, username });
        socket.send(payload);
        messageInput.value = '';
    }
});

// Отправка сообщения по нажатию Enter
messageInput.addEventListener('keydown', function(event) {
    if (event.key === 'Enter') {
        sendBtn.click();
    }
});


exitBtn.addEventListener('click', function() {
    window.location.href = `index.html`;
});