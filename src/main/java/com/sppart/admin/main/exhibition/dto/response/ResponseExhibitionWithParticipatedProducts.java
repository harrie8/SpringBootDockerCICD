package com.sppart.admin.main.exhibition.dto.response;

import com.sppart.admin.main.exhibition.dto.ExhibitionWithParticipatedProducts;
import com.sppart.admin.main.product.dto.ProductForSpecificExhibition;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ResponseExhibitionWithParticipatedProducts {

    private Long exhibitionId;
    private String title;
    private String subHeading;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private String status;
    private String poster;
    private List<ProductForSpecificExhibition> products;

    @Builder
    public ResponseExhibitionWithParticipatedProducts(Long exhibitionId, String title, String subHeading,
                                                      LocalDate startDate, LocalDate endDate, String location,
                                                      String status, String poster,
                                                      List<ProductForSpecificExhibition> products) {
        this.exhibitionId = exhibitionId;
        this.title = title;
        this.subHeading = subHeading;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.status = status;
        this.poster = poster;
        this.products = products;
    }

    public static ResponseExhibitionWithParticipatedProducts from(final ExhibitionWithParticipatedProducts exhibition) {
        return ResponseExhibitionWithParticipatedProducts.builder()
                .exhibitionId(exhibition.getExhibitionId())
                .title(exhibition.getTitle())
                .subHeading(exhibition.getSubHeading())
                .location(exhibition.getLocation())
                .startDate(exhibition.getStartDate())
                .endDate(exhibition.getEndDate())
                .status(exhibition.getStatus().getValue())
                .poster(exhibition.getPoster())
                .products(exhibition.getProducts())
                .build();
    }
}
