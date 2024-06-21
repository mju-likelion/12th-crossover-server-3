package com.example.airplaneletter.dto.response.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class PostResponseData {
    private final String title;
    private final String content;
    private final String writer;
    private final UUID postId;
    private final boolean IsMyPost;
    private final LocalDateTime createdAt;
}
