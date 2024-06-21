package com.example.airplaneletter.controller;

import com.example.airplaneletter.dto.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<ResponseDto<Void>> health() {
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "health check"), HttpStatus.OK);
    }
}
