package com.example.airplaneletter.response;

import com.example.airplaneletter.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class PostResponseData {
    private final String title;
    private final String content;
    private final String writer;
    private final List<Comment> comments;
    private final boolean isMyPost;
    private final LocalDateTime createdAt;
}