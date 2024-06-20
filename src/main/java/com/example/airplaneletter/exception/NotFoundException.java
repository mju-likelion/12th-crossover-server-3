package com.example.airplaneletter.exception;

import com.example.airplaneletter.errorCode.ErrorCode;

public class NotFoundException extends CustomException {
    public NotFoundException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}

