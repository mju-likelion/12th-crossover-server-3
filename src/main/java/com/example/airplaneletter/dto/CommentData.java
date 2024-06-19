package com.example.airplaneletter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class CommentData {
    private UUID id;
    private String content;
    private String author;
    private LocalDateTime createdAt;
}
