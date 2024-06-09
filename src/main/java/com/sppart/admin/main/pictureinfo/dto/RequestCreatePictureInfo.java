package com.sppart.admin.main.pictureinfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "작품 정보 생성 요청값")
@Getter
@NoArgsConstructor
public class RequestCreatePictureInfo {

    @Schema(description = "작품 정보 타입 - 필수", example = "고급켄트지에 디지털프린팅", required = true)
    @NotBlank
    private String type;

    @Schema(description = "작품 정보 크기 - 필수", example = "83X59cm (A1)", required = true)
    @NotBlank
    private String size;

    @Schema(description = "작품 정보 연도 - 필수", example = "2023", required = true)
    @NotNull
    private int year;

    @Builder
    public RequestCreatePictureInfo(String type, String size, int year) {
        this.type = type;
        this.size = size;
        this.year = year;
    }
}
