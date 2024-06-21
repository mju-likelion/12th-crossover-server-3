package com.example.airplaneletter.dto.response.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class CommentResponseData {
    // 댓글 내용, 닉네임, 본인 여부, 작성시간
    private String content;
    private String author;
    private boolean IsMyComment;
    private LocalDateTime createdAt;
}
