package com.sppart.admin.main.product.dto.response;

import com.sppart.admin.main.exhibition.domain.entity.ExhibitionStatus;
import com.sppart.admin.main.pictureinfo.dto.ResponsePictureInfo;
import com.sppart.admin.main.product.dto.DetailProductInfo;
import com.sppart.admin.main.productexhibition.dto.ExhibitionHistoryOfProductDto;
import com.sppart.admin.main.tag.dto.response.ResponseTag;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ResponseDetailProductInfo {

    private Long productId;
    private String picture;
    private String title;
    private String artistName;
    private String description;
    private Integer price;
    private Integer basicView;
    private Integer qrView;
    private Integer orderCount;
    private ResponsePictureInfo pictureInfo;
    private Set<ResponseTag> tags;
    private Map<ExhibitionStatus, List<ExhibitionHistoryOfProductDto>> exhibitionHistory;

    @Builder
    public ResponseDetailProductInfo(Long productId, String picture, String title, String artistName,
                                     String description, Integer price, Integer basicView, Integer qrView,
                                     Integer orderCount, ResponsePictureInfo pictureInfo, Set<ResponseTag> tags,
                                     Map<ExhibitionStatus, List<ExhibitionHistoryOfProductDto>> exhibitionHistory) {
        this.productId = productId;
        this.picture = picture;
        this.title = title;
        this.artistName = artistName;
        this.description = description;
        this.price = price;
        this.basicView = basicView;
        this.qrView = qrView;
        this.orderCount = orderCount;
        this.pictureInfo = pictureInfo;
        this.tags = tags;
        this.exhibitionHistory = exhibitionHistory;
    }

    public static ResponseDetailProductInfo from(final DetailProductInfo product,
                                                 final Map<ExhibitionStatus, List<ExhibitionHistoryOfProductDto>> exhibitionHistory) {
        return ResponseDetailProductInfo.builder()
                .productId(product.getProductId())
                .picture(product.getPicture())
                .title(product.getTitle())
                .artistName(product.getArtistName())
                .description(product.getDescription())
                .price(product.getPrice())
                .basicView(product.getBasicView())
                .qrView(product.getQrView())
                .orderCount(product.getOrderCount())
                .pictureInfo(ResponsePictureInfo.from(product.getPictureInfo()))
                .tags(product.getResponseTags())
                .exhibitionHistory(exhibitionHistory)
                .build();
    }
}
