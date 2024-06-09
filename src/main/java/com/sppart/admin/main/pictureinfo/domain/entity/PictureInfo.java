package com.sppart.admin.main.pictureinfo.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PictureInfo {

    private Long product_id;
    private String type;
    private String size;
    private int year;

    @Builder
    public PictureInfo(Long product_id, String type, String size, int year) {
        this.product_id = product_id;
        this.type = type;
        this.size = size;
        this.year = year;
    }
}
