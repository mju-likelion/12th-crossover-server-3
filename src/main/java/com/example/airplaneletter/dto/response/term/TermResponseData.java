package com.example.airplaneletter.dto.response.term;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public class TermResponseData {
    private UUID termId;
    private String content;
}
