package com.sppart.admin.main.like.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Like {
    private final Long productId;
    private final String userId;

    @Builder
    public Like(Long productId, String userId) {
        this.productId = productId;
        this.userId = userId;
    }
}
