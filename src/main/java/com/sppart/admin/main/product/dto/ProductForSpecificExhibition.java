package com.sppart.admin.main.product.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProductForSpecificExhibition {
    private long productId;
    private String title;
    private int basicView;
    private int qrView;
    private int likeCount;
    private int orderCount;
}