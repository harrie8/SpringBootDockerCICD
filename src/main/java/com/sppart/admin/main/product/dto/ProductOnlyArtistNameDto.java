package com.sppart.admin.main.product.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProductOnlyArtistNameDto {
    private long productId;
    private String artistName;

    public boolean isSameArtistName(String artistName) {
        return this.artistName.equals(artistName);
    }
}