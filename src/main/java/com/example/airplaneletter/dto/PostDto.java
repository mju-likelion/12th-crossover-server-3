package com.example.airplaneletter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostDto {
    // NotBlank 수정.
    @NotBlank(message = "제목을 입력해 주세요")
    String title;
    @NotBlank(message = "내용을 입력해 주세요")
    String content;
}
