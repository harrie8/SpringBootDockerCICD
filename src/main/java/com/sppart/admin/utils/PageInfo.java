package com.sppart.admin.utils;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PageInfo<T> {

    private final int pageIndex;
    private final int pageSize;
    private final int totalCount;
    private final List<T> data;

    @Builder
    public PageInfo(int pageIndex, int pageSize, int totalCount, List<T> data) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.data = data;
    }

    public int getTotalPage() {
        return (int) Math.ceil((double) totalCount / pageSize);
    }
}
