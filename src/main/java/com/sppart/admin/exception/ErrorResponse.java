package com.sppart.admin.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;
    private final String path;

    @JsonInclude(Include.NON_EMPTY)
    private final List<ValidationError> errors;

    @Builder
    public ErrorResponse(int status, String error, String code, String message, String path,
                         List<ValidationError> errors) {
        this.status = status;
        this.error = error;
        this.code = code;
        this.message = message;
        this.path = path;
        this.errors = errors;
    }

    @Getter
    public static class ValidationError {
        private final String field;
        private final String message;

        @Builder
        public ValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public static ValidationError of(FieldError fieldError) {
            return ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }
    }
}
