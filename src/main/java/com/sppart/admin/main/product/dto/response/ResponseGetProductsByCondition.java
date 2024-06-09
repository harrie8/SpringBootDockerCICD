package com.sppart.admin.main.product.dto.response;

import com.sppart.admin.main.pictureinfo.dto.ResponsePictureInfo;
import com.sppart.admin.main.product.dto.DetailProductInfo;
import com.sppart.admin.main.tag.dto.response.ResponseTag;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ResponseGetProductsByCondition {

    private long productId;
    private String title;
    private String artistName;
    private Set<ResponseTag> tags;
    private ResponsePictureInfo pictureInfo;
    private int price;

    @Builder
    public ResponseGetProductsByCondition(long productId, String title, String artistName, Set<ResponseTag> tags,
                                          ResponsePictureInfo pictureInfo, int price) {
        this.productId = productId;
        this.title = title;
        this.artistName = artistName;
        this.tags = tags;
        this.pictureInfo = pictureInfo;
        this.price = price;
    }

    public static ResponseGetProductsByCondition of(DetailProductInfo detailProductInfo) {
        return ResponseGetProductsByCondition.builder()
                .productId(detailProductInfo.getProductId())
                .title(detailProductInfo.getTitle())
                .artistName(detailProductInfo.getArtistName())
                .tags(detailProductInfo.getResponseTags())
                .pictureInfo(ResponsePictureInfo.from(detailProductInfo.getPictureInfo()))
                .price(detailProductInfo.getPrice())
                .build();
    }
}
