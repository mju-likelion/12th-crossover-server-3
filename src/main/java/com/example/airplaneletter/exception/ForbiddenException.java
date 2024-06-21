package com.example.airplaneletter.exception;

import com.example.airplaneletter.errorCode.ErrorCode;

public class ForbiddenException extends CustomException {
    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}