package com.sppart.admin.main.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductSearchCondition {

    private String title;
    private String artistName;
    private Long productId; // 작품 코드

    @Builder
    public ProductSearchCondition(String title, String artistName, Long productId) {
        this.title = title;
        this.artistName = artistName;
        this.productId = productId;
    }
}
