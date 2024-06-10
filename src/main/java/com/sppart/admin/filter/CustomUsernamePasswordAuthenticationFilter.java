package com.sppart.admin.filter;

import com.sppart.admin.jwt.JwtProvider;
import com.sppart.admin.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUsernamePasswordAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {"/users/login", "/swagger-ui", "/v3/api-docs", "/swagger-resources"};
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = resolveToken(request);

        try {
            if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {
                Authentication authentication = jwtProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("서명 혹은 구조가 잘못된 JWT");
            sendErrorResponse(response, 400, "구조가 잘못된 토큰입니다.");
        } catch (ExpiredJwtException e) {
            log.error("유효 기간이 만료된 토큰");
            sendErrorResponse(response, 401, "유효 기간이 만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원하는 형식과 일치하지 않는 토큰");
            sendErrorResponse(response, 400, "지원하는 형식과 다른 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("Claims가 비어있는 토큰");
            sendErrorResponse(response, 400, "값이 비어있는 토큰입니다.");
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtUtils.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtUtils.BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void sendErrorResponse(HttpServletResponse response, int sc, String msg) throws IOException {
        response.sendError(sc, msg);
    }
}
