package com.sppart.admin.main.product.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseBulkDeleteProductByIds {

    private final int productDeleteCount;
    private final int productWithTagDeleteCount;
    private final int pictureInfoDeleteCount;

    @Builder
    public ResponseBulkDeleteProductByIds(int productDeleteCount, int productWithTagDeleteCount,
                                          int pictureInfoDeleteCount) {
        this.productDeleteCount = productDeleteCount;
        this.productWithTagDeleteCount = productWithTagDeleteCount;
        this.pictureInfoDeleteCount = pictureInfoDeleteCount;
    }

    public static ResponseBulkDeleteProductByIds zero() {
        return ResponseBulkDeleteProductByIds.builder()
                .productDeleteCount(0)
                .productWithTagDeleteCount(0)
                .pictureInfoDeleteCount(0)
                .build();
    }

    @Override
    public String toString() {
        return "작품 삭제 개수: " + productDeleteCount + ", 작품태그 삭제 개수: " + productWithTagDeleteCount + ", 작품 정보 삭제 개수: "
                + pictureInfoDeleteCount;
    }
}
