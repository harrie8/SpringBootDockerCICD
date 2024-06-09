package com.sppart.admin.main.productwithtag.exception;

import com.sppart.admin.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductWithTagErrorCode implements ErrorCode {

    EMPTY_TAGS(HttpStatus.BAD_REQUEST, "작품 태그는 필수입니다."),
    INDEX_MIN(HttpStatus.BAD_REQUEST, "태그 ID는 1 이상이어야 합니다."),
    NAME_LENGTH_SUM_OVER(HttpStatus.BAD_REQUEST, "태그 이름의 합은 11자를 넘을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
