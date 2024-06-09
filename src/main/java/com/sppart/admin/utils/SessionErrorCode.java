package com.sppart.admin.utils;

import com.sppart.admin.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SessionErrorCode implements ErrorCode {

    EMPTY_SESSION(HttpStatus.BAD_REQUEST, "세션이 비어 있습니다."),
    INVALID_SESSION_VALUE(HttpStatus.BAD_REQUEST, "세션 저장 값이 올바르지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
