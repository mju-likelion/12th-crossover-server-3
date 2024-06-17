package com.example.airplaneletter.exception;

import com.example.airplaneletter.errorCode.ErrorCode;
import lombok.Getter;
@Getter
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String detail;

    public CustomException(ErrorCode e){
        this.detail = null;
        this.errorCode = e;
    }
    public CustomException(ErrorCode e, String detail){
        this.detail = detail;
        this.errorCode = e;
    }
}
