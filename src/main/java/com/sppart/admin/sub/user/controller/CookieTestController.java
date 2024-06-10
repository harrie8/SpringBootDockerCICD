package com.sppart.admin.sub.user.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CookieTestController {
    
    @PostMapping("/cookie/test")
    public ResponseEntity<Void> getMyMain() {
        ResponseCookie cookie = ResponseCookie
                .from("MYSESSIONID", "test")
                .maxAge(7 * 24 * 60 * 60)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .domain("localhost")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
