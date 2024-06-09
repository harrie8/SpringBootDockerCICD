package com.sppart.admin.main.tag.dto.request;

import com.sppart.admin.exception.CommonErrorCode;
import com.sppart.admin.exception.SuperpositionAdminException;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "태그 생성 요청값")
@Getter
@NoArgsConstructor
public class RequestCreateTag {

    @Schema(description = "태그 이름", example = "청량한", required = true)
    @NotBlank
    private String name;

    @Builder
    public RequestCreateTag(String name) {
        this.name = name;
    }

    public void validateName() {
        if (name == null || name.isEmpty()) {
            throw new SuperpositionAdminException(CommonErrorCode.INVALID_PARAMETER);
        }
    }
}
