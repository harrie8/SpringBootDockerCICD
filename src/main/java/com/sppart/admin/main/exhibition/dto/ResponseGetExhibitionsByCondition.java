package com.sppart.admin.main.exhibition.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponseGetExhibitionsByCondition {

    private final int totalCount;
    private final int findResultCount;
    private final List<ResponseExhibitionByCondition> findExhibitions;

    @Builder
    public ResponseGetExhibitionsByCondition(int totalCount, int findResultCount,
                                             List<ResponseExhibitionByCondition> findExhibitions) {
        this.totalCount = totalCount;
        this.findResultCount = findResultCount;
        this.findExhibitions = findExhibitions;
    }
}
