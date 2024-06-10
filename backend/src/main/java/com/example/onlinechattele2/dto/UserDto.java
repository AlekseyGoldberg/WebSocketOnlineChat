package com.example.onlinechattele2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {
    private String username;
    private WebSocketSession chatSession;
    private WebSocketSession onlineSession;

    public void setChatSession(WebSocketSession chatSession) {
        if (Objects.isNull(this.chatSession))
            this.chatSession = chatSession;
    }

    public void setOnlineSession(WebSocketSession onlineSession) {
        if (Objects.isNull(this.onlineSession))
            this.onlineSession = onlineSession;
    }
}
