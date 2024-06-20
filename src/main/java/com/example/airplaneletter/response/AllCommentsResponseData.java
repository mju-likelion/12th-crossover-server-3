package com.example.airplaneletter.response;

import com.example.airplaneletter.dto.CommentData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class AllCommentsResponseData {
    private List<CommentData> comments;
    private int currentPage;
    private int totalPages;
    private long totalComments;
}
