package com.sppart.admin.main.exhibition.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel
@Getter
@NoArgsConstructor
public class RequestUpdateExhibitionDisplay {
    @Min(0)
    @Max(1)
    @ApiModelProperty(value = "전시 노출 상태 값 - 0: 노출X, 1: 노출O", example = "1", required = true)
    private int isDisplay;


    @Builder
    public RequestUpdateExhibitionDisplay(int isDisplay) {
        this.isDisplay = isDisplay;
    }
}
