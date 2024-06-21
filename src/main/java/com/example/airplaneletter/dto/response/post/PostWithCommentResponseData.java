package com.example.airplaneletter.dto.response.post;

import com.example.airplaneletter.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class PostWithCommentResponseData {
    private final String title;
    private final String content;
    private final String nickname;
    private final List<Comment> comments;
    private final LocalDateTime createdAt;
    private final boolean isMyPost;
}