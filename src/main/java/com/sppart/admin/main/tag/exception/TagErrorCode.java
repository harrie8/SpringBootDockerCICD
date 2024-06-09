package com.sppart.admin.main.tag.exception;

import com.sppart.admin.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TagErrorCode implements ErrorCode {

    DUPLICATE_NAME(HttpStatus.BAD_REQUEST, "중복된 이름의 태그는 생성할 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
