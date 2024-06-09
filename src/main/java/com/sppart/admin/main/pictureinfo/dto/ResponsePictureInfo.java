package com.sppart.admin.main.pictureinfo.dto;

import com.sppart.admin.main.pictureinfo.domain.entity.PictureInfo;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponsePictureInfo {

    private String type;
    private String size;
    private int year;

    @Builder
    public ResponsePictureInfo(String type, String size, int year) {
        this.type = type;
        this.size = size;
        this.year = year;
    }

    public static ResponsePictureInfo from(PictureInfo pictureInfo) {
        return ResponsePictureInfo.builder()
                .type(pictureInfo.getType())
                .size(pictureInfo.getSize())
                .year(pictureInfo.getYear())
                .build();
    }
}
