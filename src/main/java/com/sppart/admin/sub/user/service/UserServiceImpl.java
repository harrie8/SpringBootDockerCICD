package com.sppart.admin.sub.user.service;

import com.sppart.admin.exception.SuperpositionAdminException;
import com.sppart.admin.sub.user.domain.Accessor;
import com.sppart.admin.sub.user.domain.Users;
import com.sppart.admin.sub.user.domain.mapper.UserMapper;
import com.sppart.admin.sub.user.dto.LoginDto;
import com.sppart.admin.sub.user.dto.LoginResponse;
import com.sppart.admin.sub.user.exception.UserErrorCode;
import com.sppart.admin.utils.SessionConst;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.Cookie;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public LoginResponse login(LoginDto dto) {
        Users findUser = userMapper.findById(dto.getLoginId())
                .orElseThrow(() -> new SuperpositionAdminException(UserErrorCode.ID_OR_PW_NOT_VALID));
        validPassword(dto.getLoginPassword(), findUser.getPassword());

        setSession(dto.getHttpServletRequest(), findUser);

        return LoginResponse.builder()
                .userInfo(findUser)
                .message("success")
                .build();
    }

    @Override
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
    }

    private void validPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new SuperpositionAdminException(UserErrorCode.ID_OR_PW_NOT_VALID);
        }
    }

    private void setSession(HttpServletRequest httpServletRequest, Users findUser) {
        HttpSession session = httpServletRequest.getSession();
        Accessor accessor = Accessor.builder()
                .id(findUser.getId())
                .role(findUser.getRole())
                .build();
        session.setAttribute(SessionConst.LOGIN_USER, accessor);
        session.setMaxInactiveInterval(SessionConst.DEFAULT_TIME_OUT_SECONDS);

        // 직접 쿠키를 생성하여 SameSite 속성 설정
        Cookie cookie = new Cookie("SESSIONID", session.getId());
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // HTTPS에서만 전송
        cookie.setPath("/");
        cookie.setMaxAge(SessionConst.DEFAULT_TIME_OUT_SECONDS);
        cookie.setDomain("yourdomain.com"); // 필요한 경우 도메인 설정
        cookie.setSameSite("None"); // SameSite 속성 설정
    
        httpServletResponse.addCookie(cookie);
    }
}
