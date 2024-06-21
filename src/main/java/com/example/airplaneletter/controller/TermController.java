package com.example.airplaneletter.controller;

import com.example.airplaneletter.dto.CreateTermDto;
import com.example.airplaneletter.dto.ResponseDto;
import com.example.airplaneletter.response.AllTermsResponseData;
import com.example.airplaneletter.service.TermService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class TermController {
    private final TermService termService;
    @GetMapping("/terms")
    public ResponseEntity<ResponseDto<AllTermsResponseData>> getAllTerms() {
            AllTermsResponseData allTermsResponseData = this.termService.getAllTerms();
            return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "ok", allTermsResponseData), HttpStatus.OK);
    }

    @PostMapping("/terms")
    public ResponseEntity<ResponseDto<Void>> createTerm(@RequestBody CreateTermDto createTermDto){
            this.termService.createTerm(createTermDto);
            return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "ok"), HttpStatus.CREATED);
    }
}
