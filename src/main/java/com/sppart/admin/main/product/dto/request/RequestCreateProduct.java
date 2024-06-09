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
public class RequestCreateProduct {

    @Schema(description = "작품 명 - 필수", example = "roses", required = true)
    @NotBlank
    private String title;
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

    @Builder
    public RequestCreateProduct(String title, String artistName, String description,
                                RequestCreatePictureInfo pictureInfo, Set<Long> tagIds, int price) {
        this.title = title;
        this.artistName = artistName;
        this.description = description;
        this.pictureInfo = pictureInfo;
        this.tagIds = tagIds;
        this.price = price;
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
