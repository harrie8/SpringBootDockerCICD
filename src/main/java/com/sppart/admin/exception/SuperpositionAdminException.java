package com.sppart.admin.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class SuperpositionAdminException extends RuntimeException {

    private final ErrorCode errorCode;
}
