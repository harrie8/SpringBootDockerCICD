package com.sppart.admin.main.product.domain.entity;

import com.sppart.admin.main.product.dto.request.RequestCreateProduct;
import lombok.Builder;
import lombok.Data;

@Data
public class Product {

    private final Long product_id;
    private final String picture;
    private final String title;
    private final String artistName;
    private final String description;
    private final int price;
    private final int basicView;
    private final int qrView;
    private final int orderCount;

    @Builder
    public Product(Long product_id, String picture, String title, String artistName, String description, int price,
                   int basicView, int qrView, int orderCount) {
        this.product_id = product_id;
        this.picture = picture;
        this.title = title;
        this.artistName = artistName;
        this.description = description;
        this.price = price;
        this.basicView = basicView;
        this.qrView = qrView;
        this.orderCount = orderCount;
    }

    public static Product create(String picture, RequestCreateProduct req) {
        return Product.builder()
                .picture(picture)
                .title(req.getTitle())
                .artistName(req.getArtistName())
                .description(req.getDescription())
                .price(req.getPrice())
                .basicView(0)
                .qrView(0)
                .orderCount(0)
                .build();
    }
}
