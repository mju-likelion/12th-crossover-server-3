package com.example.airplaneletter.service;

import com.example.airplaneletter.dto.CreateTermDto;
import com.example.airplaneletter.model.Term;
import com.example.airplaneletter.repository.TermRepository;
import com.example.airplaneletter.response.AllTermsResponseData;
import com.example.airplaneletter.response.TermResponseData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TermService {
    private final TermRepository termRepository;

    public AllTermsResponseData getAllTerms() {
        List<TermResponseData> termList = new ArrayList<>();

        for (Term t : termRepository.findAll()) {
            TermResponseData termResponseData = TermResponseData.builder()
                    .termId(t.getId())
                    .content(t.getContent())
                    .build();
            termList.add(termResponseData);
        }
        AllTermsResponseData termResponseDataList = AllTermsResponseData.builder().terms(termList).build();
        return termResponseDataList;
    }

    public void createTerm(CreateTermDto createTermDto){

        Term term = Term.builder()
                .content(createTermDto.getContent())
                .build();
        this.termRepository.save(term);
    }
}
