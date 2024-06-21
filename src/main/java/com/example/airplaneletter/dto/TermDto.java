package com.example.airplaneletter.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class TermDto {
    private UUID termId;
    @NotNull
    private boolean agreed;
}
