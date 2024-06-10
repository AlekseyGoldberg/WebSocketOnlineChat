package com.example.onlinechattele2.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
@Slf4j
public class OnlineWebsocketHandler extends TextWebSocketHandler {
    private final ConnectionWebsocketHandler connectionWebsocketHandler;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = String.valueOf(session.getAttributes().get("username"));
        log.info("{} connected OnlineWebsocketHandler", username);
        connectionWebsocketHandler.addConnection(username, null, session);
        connectionWebsocketHandler.sendAllUsersInOnline();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = String.valueOf(session.getAttributes().get("username"));
        log.info("{} disconnected OnlineWebsocketHandler", username);
        connectionWebsocketHandler.closeConnection(username);
        super.afterConnectionClosed(session, status);
    }
}
