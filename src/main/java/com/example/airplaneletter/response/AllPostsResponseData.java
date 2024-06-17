package com.example.airplaneletter.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class AllPostsResponseData {
    private final String title;
    private final String content;
    private final String writer;
    private final boolean isMyPost;
    private final LocalDateTime createdAt;
}
