package com.example.airplaneletter.exception;

import com.example.airplaneletter.errorCode.ErrorCode;

public class DtoValidationException extends CustomException {
    public DtoValidationException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}