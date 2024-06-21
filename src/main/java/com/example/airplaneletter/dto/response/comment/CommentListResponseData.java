package com.example.airplaneletter.dto.response.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class CommentListResponseData {
    private List<CommentResponseData> comments;
    private int currentPage;
    private int totalPages;
    private long totalComments;
}
