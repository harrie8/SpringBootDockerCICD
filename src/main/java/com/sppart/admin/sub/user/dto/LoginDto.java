package com.sppart.admin.sub.user.dto;

import javax.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginDto {

    private final LoginRequest loginRequest;
    private final HttpServletRequest httpServletRequest;

    @Builder
    public LoginDto(LoginRequest loginRequest, HttpServletRequest httpServletRequest) {
        this.loginRequest = loginRequest;
        this.httpServletRequest = httpServletRequest;
    }

    public String getLoginId() {
        return loginRequest.getId();
    }

    public String getLoginPassword() {
        return loginRequest.getPassword();
    }
}
