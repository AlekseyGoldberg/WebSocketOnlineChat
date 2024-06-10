package com.example.onlinechattele2.handler;

import com.example.onlinechattele2.dto.MessageDto;
import com.example.onlinechattele2.entity.MessageEntity;
import com.example.onlinechattele2.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final MessageService messageService;
    private final ObjectMapper objectMapper;
    private final ConnectionWebsocketHandler connectionWebsocketHandler;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        try {
            String username = String.valueOf(session.getAttributes().get("username"));
            log.info("{} connected ChatWebSocketHandler", username);
            connectionWebsocketHandler.addConnection(username, session, null);
            connectionWebsocketHandler.sendAllUsersInOnline();
            messageService.findAllMessage().forEach(m -> {
                try {
                    connectionWebsocketHandler.sendMessageInSession(session, new TextMessage(
                            objectMapper.writeValueAsString(m)
                    ));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            afterConnectionClosed(session, new CloseStatus(500, e.getMessage()));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            MessageDto messageDto = objectMapper.readValue(message.getPayload(), MessageDto.class);
            log.info("message received: {}", messageDto);
            MessageEntity saveMessage = messageService.save(messageDto.getMessage(), messageDto.getUsername());
            connectionWebsocketHandler.sendMessageAllChatConnections(saveMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            afterConnectionClosed(session, new CloseStatus(500, e.getMessage()));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        try {
            String username = String.valueOf(session.getAttributes().get("username"));
            log.info("{} disconnected ChatWebSocketHandler", username);
            connectionWebsocketHandler.closeConnection(username);
            connectionWebsocketHandler.sendAllUsersInOnline();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            super.afterConnectionClosed(session, status);
        }
    }
}
