// Создаем WebSocket соединение
const socketOnline = new WebSocket('ws://localhost:8085/chat/online?username='+username);
const online = document.getElementById('online');


// Обработчик открытия соединения
socketOnline.onopen = function() {
    console.log("the connection is established")
};

// Обработчик получения сообщений
socketOnline.onmessage = function(event) {
    try {
        const msg = JSON.parse(event.data);
        const online = document.getElementById('online'); // Получаем ссылку на блок "online"
        
        // Очищаем содержимое блока "online"
        online.innerHTML = 'Users in online:';
        
        msg.forEach((value, index) => {
            const messageDiv = document.createElement('div');
            messageDiv.classList.add('message');
        
            const usernameSpan = document.createElement('span');
            usernameSpan.classList.add('username');
            usernameSpan.textContent = value ? value : 'Anonymous: ';  
            messageDiv.appendChild(usernameSpan);
            online.appendChild(messageDiv);
            
            online.scrollTop = online.scrollHeight;
        });
    } catch (e) {
        console.error('Error parsing JSON', e);
    }
};

// Обработчик закрытия соединения
socketOnline.onclose = function() {
    console.log('WebSocket connection closed');
};

// Обработчик ошибок
socketOnline.onerror = function(error) {
    console.error('WebSocket error:', error);
};
