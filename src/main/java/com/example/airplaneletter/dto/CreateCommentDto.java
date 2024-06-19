package com.example.airplaneletter.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCommentDto {

    @NotBlank(message = "내용이 비었습니다.")
    private String content;
}
