package com.sppart.admin.sub.user.exception;

import com.sppart.admin.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    ID_OR_PW_NOT_VALID(HttpStatus.BAD_REQUEST, "아이디 또는 비밀번호를 확인하세요."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
