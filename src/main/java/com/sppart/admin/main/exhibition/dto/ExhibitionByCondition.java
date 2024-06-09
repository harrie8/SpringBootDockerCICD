package com.sppart.admin.main.exhibition.dto;

import com.sppart.admin.main.exhibition.domain.entity.ExhibitionStatus;
import com.sppart.admin.main.product.dto.ProductOnlyArtistNameDto;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter // for mybatis
@NoArgsConstructor // for mybatis
public class ExhibitionByCondition {

    private Long exhibitionId;
    private String title;
    private String subHeading;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private ExhibitionStatus status;
    private int isDisplay;
    private List<ProductOnlyArtistNameDto> products;

    @Builder
    public ExhibitionByCondition(Long exhibitionId, String title, String subHeading, String location,
                                 LocalDate startDate, LocalDate endDate, ExhibitionStatus status, int isDisplay,
                                 List<ProductOnlyArtistNameDto> products) {
        this.exhibitionId = exhibitionId;
        this.title = title;
        this.subHeading = subHeading;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.status = status;
        this.isDisplay = isDisplay;
        this.products = products;
    }
}
