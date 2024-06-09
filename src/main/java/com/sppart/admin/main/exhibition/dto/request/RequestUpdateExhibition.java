package com.sppart.admin.main.exhibition.dto.request;

import com.sppart.admin.main.exhibition.domain.entity.ExhibitionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Schema(description = "전시 생성 요청값")
@Getter
@NoArgsConstructor
public class RequestUpdateExhibition {

    @Schema(description = "전시 제목 - 필수", example = "제목입니다.", required = true)
    @NotBlank
    private String title;
    @Schema(description = "전시 부제목 - 필수", example = "부제목입니다.", required = true)
    @NotBlank
    private String subHeading;
    @Schema(description = "시작일 - 필수", required = true, type = "string")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @Schema(description = "종료일 - 필수", required = true, type = "string")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @Schema(description = "전시 장소 - 필수", example = "장소", required = true)
    @NotBlank
    private String location;
    @Schema(description = "전시 상태 - 필수", example = "current", required = true)
    @NotNull
    private ExhibitionStatus status;
    @Schema(description = "기존 포스터 이미지 URL - 필수", example = "ddbe4913-271f-4fd5-846c-f9fa39e788f7.jpg", required = true)
    @NotBlank
    private String oldPoster;
    @Schema(description = "전시에 참여할 작품 번호들", example = "[1,2,3]")
    private Set<Long> productIds;

    @Builder
    public RequestUpdateExhibition(String title, String subHeading, LocalDate startDate, LocalDate endDate,
                                   String location, ExhibitionStatus status, String oldPoster, Set<Long> productIds) {
        this.title = title;
        this.subHeading = subHeading;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.status = status;
        this.productIds = productIds;
        this.oldPoster = oldPoster;
    }
}
