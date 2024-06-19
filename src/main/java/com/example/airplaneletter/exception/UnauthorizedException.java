package com.example.airplaneletter.exception;

import com.example.airplaneletter.errorCode.ErrorCode;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}

