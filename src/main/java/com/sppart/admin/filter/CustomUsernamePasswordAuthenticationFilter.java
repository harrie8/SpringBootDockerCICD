package com.sppart.admin.filter;

import com.sppart.admin.sub.user.domain.Accessor;
import com.sppart.admin.utils.SessionConst;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUsernamePasswordAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {"/users/login", "/swagger-ui", "/v3/api-docs", "/swagger-resources"};
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            HttpSession session = getHttpSession(request);
            Accessor accessor = getAccessor(request, session);
            setAuthentication(accessor);
        } catch (IllegalStateException e) {
            log.error("세션 오류");
        }

        filterChain.doFilter(request, response);
    }

    private HttpSession getHttpSession(HttpServletRequest request) throws IllegalStateException {
        HttpSession session = request.getSession(false);
        if (isEmptySession(session)) {
            log.error("세션이 비어 있는 오류");
            throw new IllegalStateException();
        }
        return session;
    }

    private boolean isEmptySession(HttpSession session) {
        return session == null;
    }

    private Accessor getAccessor(HttpServletRequest request, HttpSession session) throws IllegalStateException {
        Object sessionAttribute = session.getAttribute(SessionConst.LOGIN_USER);
        if (!(sessionAttribute instanceof Accessor accessor)) {
            printCookie(request);
            log.error("세션에 Accessor 객체가 아닌 {} 값이 설정되어 있음", sessionAttribute);
            throw new IllegalStateException();
        }
        return accessor;
    }

    private void printCookie(HttpServletRequest request) {
        Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("JSESSIONID"))
                .findFirst()
                .ifPresent(cookie -> log.info("cookie.getName() = {}, cookie.getValue() = {}", cookie.getName(),
                        cookie.getValue()));
    }

    private void setAuthentication(Accessor accessor) {
        UserDetails principal = new User(accessor.getId(), "", accessor.getGrantedAuthorities());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                principal, "", accessor.getRole().getGrantedAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
