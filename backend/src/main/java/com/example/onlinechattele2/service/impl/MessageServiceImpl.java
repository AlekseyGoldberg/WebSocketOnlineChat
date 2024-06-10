package com.example.onlinechattele2.service.impl;

import com.example.onlinechattele2.entity.MessageEntity;
import com.example.onlinechattele2.repository.MessageRepository;
import com.example.onlinechattele2.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public List<MessageEntity> findAllMessage() {
        return messageRepository.findAll();
    }

    @Override
    public MessageEntity save(String message, String username) {
        return messageRepository.save(
                MessageEntity.builder()
                        .username(username)
                        .message(message)
                        .build()
        );
    }
}
