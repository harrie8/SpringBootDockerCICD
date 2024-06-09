package com.sppart.admin.main.exhibition.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseBulkDeleteByIds {

    private final int exhibitionDeleteCount;
    private final int productExhibitionDeleteCount;

    @Builder
    public ResponseBulkDeleteByIds(int exhibitionDeleteCount, int productExhibitionDeleteCount) {
        this.exhibitionDeleteCount = exhibitionDeleteCount;
        this.productExhibitionDeleteCount = productExhibitionDeleteCount;
    }

    public static ResponseBulkDeleteByIds zero() {
        return ResponseBulkDeleteByIds.builder()
                .exhibitionDeleteCount(0)
                .productExhibitionDeleteCount(0)
                .build();
    }

    @Override
    public String toString() {
        return "전시 삭제 개수: " + exhibitionDeleteCount + ", 작품전시 삭제 개수: " + productExhibitionDeleteCount;
    }
}
