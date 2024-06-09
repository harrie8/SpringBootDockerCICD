package com.sppart.admin.main.exhibition.domain.entity;

import com.sppart.admin.exception.SuperpositionAdminException;
import com.sppart.admin.main.exhibition.exception.ExhibitionErrorCode;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExhibitionStatus {
    current("전시중"),
    upcoming("전시 예정"),
    end("전시 종료");

    private final String value;

    public static ExhibitionStatus findByName(String name) {
        return Arrays.stream(values())
                .filter(exhibitionStatus -> exhibitionStatus.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new SuperpositionAdminException(ExhibitionErrorCode.NOT_FOUND_STATUS));
    }
}
