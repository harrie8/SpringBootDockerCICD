package com.sppart.admin.main.product.exception;

import com.sppart.admin.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 작품입니다."),
    NOT_FOUND_STATUS(HttpStatus.NOT_FOUND, "존재하지 않는 전시 상태입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
