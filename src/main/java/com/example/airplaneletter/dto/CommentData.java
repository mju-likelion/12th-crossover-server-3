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
    // 댓글 내용, 닉네임, 본인 여부, 작성시간
    private String content;
    private String author;
    private boolean isMine;
    private LocalDateTime createdAt;
}
