package com.sppart.admin.main.exhibition.dto;

import com.sppart.admin.main.exhibition.domain.entity.ExhibitionStatus;
import com.sppart.admin.main.product.dto.ProductForSpecificExhibition;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ExhibitionWithParticipatedProducts {

    private Long exhibitionId;
    private String title;
    private String subHeading;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private ExhibitionStatus status;
    private String poster;
    private List<ProductForSpecificExhibition> products;

    public void sortProductsByProductId() {
        products.sort(Comparator.comparing(ProductForSpecificExhibition::getProductId));
    }
}
