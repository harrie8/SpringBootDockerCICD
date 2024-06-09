package com.sppart.admin.main.exhibition.domain.entity;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Exhibition {

    private final Long id;
    private final String title;
    private final String subHeading;
    private final String location;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final ExhibitionStatus status;
    private final String poster;
    private int isDisplay;

    @Builder
    public Exhibition(Long id, String title, String subHeading, String location, LocalDate startDate, LocalDate endDate,
                      ExhibitionStatus status, String poster, int isDisplay) {
        this.id = id;
        this.title = title;
        this.subHeading = subHeading;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.poster = poster;
        this.isDisplay = isDisplay;
    }

    public void changeDisplay(int isDisplay) {
        this.isDisplay = isDisplay;
    }
}
