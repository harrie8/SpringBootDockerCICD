package com.sppart.admin.main.productexhibition.dto;

import com.sppart.admin.main.exhibition.domain.entity.ExhibitionStatus;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ResponseExhibitionHistory {

    private Map<ExhibitionStatus, List<ExhibitionHistoryOfProductDto>> statusMap;

    @Builder
    public ResponseExhibitionHistory(
            Map<ExhibitionStatus, List<ExhibitionHistoryOfProductDto>> statusMap) {
        this.statusMap = statusMap;
    }

    public static ResponseExhibitionHistory from(
            Map<ExhibitionStatus, List<ExhibitionHistoryOfProductDto>> statusMap) {

        return ResponseExhibitionHistory.builder()
                .statusMap(statusMap)
                .build();
    }
}
