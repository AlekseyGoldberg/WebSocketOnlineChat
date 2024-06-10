package com.example.onlinechattele2.service;

import com.example.onlinechattele2.entity.MessageEntity;

import java.util.List;

public interface MessageService {
    List<MessageEntity> findAllMessage();

    MessageEntity save(String message, String author);

}
