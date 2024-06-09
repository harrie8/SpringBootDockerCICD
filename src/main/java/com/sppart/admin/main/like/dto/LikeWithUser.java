package com.sppart.admin.main.like.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeWithUser {
    private final Long productId;
    private final String userEmail;
    private final String userNickname;

    @Builder
    public LikeWithUser(Long productId, String userEmail, String userNickname) {
        this.productId = productId;
        this.userEmail = userEmail;
        this.userNickname = userNickname;
    }
}
