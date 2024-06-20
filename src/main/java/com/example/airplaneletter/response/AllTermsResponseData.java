package com.example.airplaneletter.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class AllTermsResponseData {
    private List<TermResponseData> terms;
}
