package com.sppart.admin.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtToken {
    private String id;
    private String grantType; // Bearer
    private String accessToken;
}
