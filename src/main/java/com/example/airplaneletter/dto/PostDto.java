package com.example.airplaneletter.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostDto {
    @NotNull(message = "제목을 입력해 주세요")
    String title;
    @NotNull(message = "내용을 입력해 주세요")
    String content;
}
