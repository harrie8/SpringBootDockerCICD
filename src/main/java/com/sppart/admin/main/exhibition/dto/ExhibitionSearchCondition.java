package com.sppart.admin.main.exhibition.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExhibitionSearchCondition {

    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String artistName;

    @Builder
    public ExhibitionSearchCondition(LocalDate startDate, LocalDate endDate, String title, String artistName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.artistName = artistName;
    }

    public boolean hasArtistName() {
        return artistName != null && !artistName.isBlank();
    }
}
