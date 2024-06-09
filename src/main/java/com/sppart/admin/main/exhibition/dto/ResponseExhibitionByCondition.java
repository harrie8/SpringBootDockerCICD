package com.sppart.admin.main.exhibition.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sppart.admin.main.product.dto.ProductOnlyArtistNameDto;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseExhibitionByCondition {

    private final Long exhibitionId;
    private final String title;
    private final String subHeading;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String location;
    private final String status;
    private final int isDisplay;
    @JsonIgnore
    private final Set<String> artistNames;

    @Builder
    public ResponseExhibitionByCondition(Long exhibitionId, String title, String subHeading, LocalDate startDate,
                                         LocalDate endDate, String location, String status, int isDisplay,
                                         Set<String> artistNames) {
        this.exhibitionId = exhibitionId;
        this.title = title;
        this.subHeading = subHeading;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.status = status;
        this.isDisplay = isDisplay;
        this.artistNames = artistNames;
    }

    public static ResponseExhibitionByCondition from(final ExhibitionByCondition exhibition) {
        return ResponseExhibitionByCondition.builder()
                .exhibitionId(exhibition.getExhibitionId())
                .title(exhibition.getTitle())
                .subHeading(exhibition.getSubHeading())
                .startDate(exhibition.getStartDate())
                .endDate(exhibition.getEndDate())
                .location(exhibition.getLocation())
                .status(exhibition.getStatus().getValue())
                .isDisplay(exhibition.getIsDisplay())
                .artistNames(exhibition.getProducts().stream()
                        .map(ProductOnlyArtistNameDto::getArtistName)
                        .collect(Collectors.toSet()))
                .build();
    }
}
