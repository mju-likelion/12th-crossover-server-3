package com.example.airplaneletter.exception;

import com.example.airplaneletter.errorCode.ErrorCode;

public class ConflictException extends CustomException {
    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}