package com.example.airplaneletter.service;

import com.example.airplaneletter.dto.term.CreateTermDto;
import com.example.airplaneletter.model.Term;
import com.example.airplaneletter.repository.TermRepository;
import com.example.airplaneletter.dto.response.term.TermListResponseData;
import com.example.airplaneletter.dto.response.term.TermResponseData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TermService {
    private final TermRepository termRepository;

    public TermListResponseData getAllTerms() {
        List<TermResponseData> termList = new ArrayList<>();

        for (Term t : termRepository.findAll()) {
            TermResponseData termResponseData = TermResponseData.builder()
                    .termId(t.getId())
                    .content(t.getContent())
                    .build();
            termList.add(termResponseData);
        }
        TermListResponseData termResponseDataList = TermListResponseData.builder().terms(termList).build();
        return termResponseDataList;
    }

    public void createTerm(CreateTermDto createTermDto){

        Term term = Term.builder()
                .content(createTermDto.getContent())
                .build();
        this.termRepository.save(term);
    }
}
