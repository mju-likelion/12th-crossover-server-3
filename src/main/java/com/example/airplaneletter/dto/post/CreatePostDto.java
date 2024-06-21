package com.example.airplaneletter.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreatePostDto {
    // NotBlank 수정.
    @NotBlank(message = "제목을 입력해 주세요")
    String title;
    @NotBlank(message = "내용을 입력해 주세요")
    String content;
}
