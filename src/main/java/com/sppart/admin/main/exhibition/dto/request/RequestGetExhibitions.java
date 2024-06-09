package com.sppart.admin.main.exhibition.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@ApiModel
@Data
@NoArgsConstructor
public class RequestGetExhibitions {

    @ApiModelProperty(value = "시작일 - 2024-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @ApiModelProperty(value = "종료일 - 2024-01-02")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @ApiModelProperty(value = "전시 제목 - 여기는 따뜻해")
    private String title;
    @ApiModelProperty(value = "작가 이름 - 문소")
    private String artistName;

    @Builder
    public RequestGetExhibitions(LocalDate startDate, LocalDate endDate, String title, String artistName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.artistName = artistName;
    }
}
