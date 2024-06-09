package com.sppart.admin.main.product.dto;

import com.sppart.admin.main.pictureinfo.domain.entity.PictureInfo;
import com.sppart.admin.main.tag.domain.entity.Tag;
import com.sppart.admin.main.tag.dto.response.ResponseTag;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetailProductInfo {

    private Long productId;
    private String picture;
    private String title;
    private String artistName;
    private String description;
    private Integer price;
    private Integer basicView;
    private Integer qrView;
    private Integer orderCount;
    private PictureInfo pictureInfo;
    private Set<Tag> tags;

    @Builder
    public DetailProductInfo(Long productId, String picture, String title, String artistName, String description,
                             Integer price, Integer basicView, Integer qrView, Integer orderCount,
                             PictureInfo pictureInfo, Set<Tag> tags) {

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
    }

    public Set<ResponseTag> getResponseTags() {
        return tags.stream()
                .map(ResponseTag::from)
                .collect(Collectors.toUnmodifiableSet());
    }
}
