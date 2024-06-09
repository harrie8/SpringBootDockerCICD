package com.sppart.admin.main.product.dto.request;

import com.sppart.admin.exception.SuperpositionAdminException;
import com.sppart.admin.main.pictureinfo.dto.RequestCreatePictureInfo;
import com.sppart.admin.main.productwithtag.exception.ProductWithTagErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "작품 생성 요청값")
@Getter
@NoArgsConstructor
public class RequestUpdateProduct {

    @Schema(description = "작품 명 - 필수", example = "roses", required = true)
    @NotBlank
    private String title;
    @Schema(description = "기존 이미지 URL - 필수", example = "e2108777-da4a-4869-9540-6b0308d708a9.jpg", required = true)
    @NotBlank
    private String oldPicture;
    @Schema(description = "작가 명 - 필수", example = "문소", required = true)
    @NotBlank
    private String artistName;
    @Schema(description = "작품 설명 - 필수", example = "나를 위로해주는 아름다운 장미, 그리고 음악과 함께 떠오르는 아련한 기억", required = true)
    @NotBlank
    private String description;
    @Valid
    private RequestCreatePictureInfo pictureInfo;
    @Schema(description = "작품 태그들 - 필수", type = "java.util.Set", required = true)
    @NotNull
    private Set<Long> tagIds;
    @Schema(description = "가격 - 필수", example = "250000", required = true)
    @NotNull
    private int price;
    @Schema(description = "일반 조회수 - 필수", example = "0", required = true)
    @NotNull
    private int basicView;
    @Schema(description = "QR 조회수 - 필수", example = "0", required = true)
    @NotNull
    private int qrView;
    @Schema(description = "좋아요 수 - 필수", example = "0", required = true)
    @NotNull
    private int likeCount;
    @Schema(description = "주문 수 - 필수", example = "0", required = true)
    @NotNull
    private int orderCount;

    @Builder
    public RequestUpdateProduct(String title, String oldPicture, String artistName, String description,
                                RequestCreatePictureInfo pictureInfo, Set<Long> tagIds, int price, int basicView,
                                int qrView, int likeCount, int orderCount) {
        this.title = title;
        this.oldPicture = oldPicture;
        this.artistName = artistName;
        this.description = description;
        this.pictureInfo = pictureInfo;
        this.tagIds = tagIds;
        this.price = price;
        this.basicView = basicView;
        this.qrView = qrView;
        this.likeCount = likeCount;
        this.orderCount = orderCount;
    }

    public void validateTags() {
        if (tagIds.isEmpty()) {
            throw new SuperpositionAdminException(ProductWithTagErrorCode.EMPTY_TAGS);
        }
        boolean isTagIdLessThanOne = tagIds.stream()
                .anyMatch(tagId -> tagId < 1);
        if (isTagIdLessThanOne) {
            throw new SuperpositionAdminException(ProductWithTagErrorCode.INDEX_MIN);
        }
    }
}
