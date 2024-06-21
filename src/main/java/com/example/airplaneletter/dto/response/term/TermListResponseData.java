package com.example.airplaneletter.dto.response.term;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class TermListResponseData {
    private List<TermResponseData> terms;
}
