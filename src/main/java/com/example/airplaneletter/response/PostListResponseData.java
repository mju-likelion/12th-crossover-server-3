package com.example.airplaneletter.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class PostListResponseData {
    private final String title;
    private final String content;
    private final String nickname;
    private final LocalDateTime createdAt;
    private final boolean isMyPost;
}
