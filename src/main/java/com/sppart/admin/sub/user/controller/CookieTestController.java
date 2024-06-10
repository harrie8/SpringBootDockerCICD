package com.sppart.admin.sub.user.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CookieTestController {

    @GetMapping("/cookie/test")
    public String cookieTest(HttpServletResponse response) {
        Cookie cookie = new Cookie("myCookie", "myCookieValue");

        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(7 * 24 * 60 * 60);

        // SameSite=None 설정 추가
        response.addHeader("Set-Cookie", String.format("%s; SameSite=None; Secure", cookie.toString()));

        return "Cookie is set";
    }
}
