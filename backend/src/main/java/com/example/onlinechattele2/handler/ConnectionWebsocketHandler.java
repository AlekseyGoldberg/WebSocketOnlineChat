package com.example.onlinechattele2.handler;

import com.example.onlinechattele2.dto.UserDto;
import com.example.onlinechattele2.entity.MessageEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionWebsocketHandler {
    private final ObjectMapper objectMapper;
    private volatile ConcurrentHashMap<String, UserDto> connections = new ConcurrentHashMap<>();

    public void addConnection(String username, WebSocketSession chatSession, WebSocketSession onlineSession) throws Exception {
        if (Objects.isNull(username)) {
            username = "Anon";
        }
        UserDto currentUser = connections.get(username);
        if (Objects.isNull(currentUser)) {
            connections.put(username,
                    UserDto.builder()
                            .chatSession(chatSession)
                            .onlineSession(onlineSession)
                            .build());
        } else {
            currentUser.setChatSession(chatSession);
            currentUser.setOnlineSession(onlineSession);
        }
    }

    public void closeConnection(String username) throws Exception {
        if (Objects.isNull(username)) {
            username = "Anon";
        }
        connections.remove(username);
    }

    public void sendMessageAllChatConnections(MessageEntity messageEntity) throws IOException {
        log.info("Send message: {} all chats", messageEntity);
        for (UserDto user : connections.values()) {
            user.getChatSession().sendMessage(new TextMessage(
                    objectMapper.writer()
                            .withDefaultPrettyPrinter()
                            .writeValueAsString(messageEntity)));
        }
    }

    public void sendAllUsersInOnline() throws IOException {
        List<String> allUsersInOnline = connections.keySet().stream()
                .toList();
        log.info("Send list all users:{} in online", allUsersInOnline);
        for (UserDto user : connections.values()) {

            WebSocketSession onlineSession = user.getOnlineSession();
            if (Objects.nonNull(onlineSession)) {
                onlineSession.sendMessage(
                        new TextMessage(objectMapper.writeValueAsString(allUsersInOnline))
                );
            }
        }
    }

    public void sendMessageInSession(WebSocketSession webSocketSession, TextMessage message) throws IOException {
        webSocketSession.sendMessage(message);
    }
}
