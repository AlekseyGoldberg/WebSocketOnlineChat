package com.example.onlinechattele2.mapper;

import com.example.onlinechattele2.dto.MessageDto;
import com.example.onlinechattele2.entity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MessageMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "username", source = "username")
    MessageDto toDto(MessageEntity messageEntity);
}
