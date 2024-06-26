package com.example.airplaneletter.dto.term;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class AgreeToTermDto {
    @NotBlank(message = "약관 id가 비었습니다.")
    private UUID termId;

    @AssertTrue
    private boolean agreed;
}
