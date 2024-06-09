package com.sppart.admin.main.product.dto.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@NoArgsConstructor
public class RequestGetProducts {

    @Schema(description = "작품 명 - roses", example = "roses")
    private String title;
    @ApiModelProperty(value = "작가 이름 - 문소", example = "문소")
    private String artistName;
    @ApiModelProperty(value = "작가 코드 - 1", example = "1")
    private Long productId;
}
