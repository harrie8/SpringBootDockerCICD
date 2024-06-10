package com.sppart.admin.sub.user.dto;

import com.sppart.admin.sub.user.domain.Users;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

    private final Users userInfo;
    private final String accessToken;
    private final String message;

    @Builder
    public LoginResponse(Users userInfo, String accessToken, String message) {
        this.userInfo = userInfo;
        this.accessToken = accessToken;
        this.message = message;
    }
}
