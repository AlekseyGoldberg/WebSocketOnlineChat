 function getQueryParams() {
         const params = {};
         const queryString = window.location.search.substring(1);
         const regex = /([^&=]+)=([^&]*)/g;
         let match;
         while (match = regex.exec(queryString)) {
             params[decodeURIComponent(match[1])] = decodeURIComponent(match[2]);
         }
         return params;
     }

     const params = getQueryParams();
     const username = params.username;

     if (!username) {
         alert('Username is missing');
         window.location.href = 'login.html';
     }

     const chat = document.getElementById('chat');
     const messageInput = document.getElementById('message');
     const sendBtn = document.getElementById('sendBtn');

     // Создаем WebSocket соединение
     const socket = new WebSocket('ws://localhost:8085/chat?username='+username);



     // Обработчик открытия соединения
     socket.onopen = function() {
         console.log("the connection is established")
     };

     // Обработчик получения сообщений
     socket.onmessage = function(event) {
         try {
             const msg = JSON.parse(event.data);
             const messageDiv = document.createElement('div');
             messageDiv.classList.add('message');

             const usernameSpan = document.createElement('span');
             usernameSpan.classList.add('username');
             usernameSpan.textContent = msg.username ? msg.username + ': ' : 'Anonymous: ';

             const messageSpan = document.createElement('span');
             messageSpan.textContent = msg.message;

             messageDiv.appendChild(usernameSpan);
             messageDiv.appendChild(messageSpan);
             chat.appendChild(messageDiv);

             chat.scrollTop = chat.scrollHeight;
         } catch (e) {
             console.error('Error parsing JSON', e);
         }
     };

     // Обработчик закрытия соединения
     socket.onclose = function() {
         console.log('WebSocket connection closed');
     };

     // Обработчик ошибок
     socket.onerror = function(error) {
         console.error('WebSocket error:', error);
     };