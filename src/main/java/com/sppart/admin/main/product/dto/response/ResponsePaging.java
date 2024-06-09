package com.sppart.admin.main.product.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponsePaging<T> {

    private final int totalCount;
    private final int findResultCount;
    private final List<T> findDomains;

    @Builder
    public ResponsePaging(int totalCount, int findResultCount, List<T> findDomains) {
        this.totalCount = totalCount;
        this.findResultCount = findResultCount;
        this.findDomains = findDomains;
    }
}
