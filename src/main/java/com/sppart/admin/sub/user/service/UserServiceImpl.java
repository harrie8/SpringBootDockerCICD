package com.sppart.admin.sub.user.service;

import com.sppart.admin.exception.SuperpositionAdminException;
import com.sppart.admin.jwt.JwtProvider;
import com.sppart.admin.jwt.JwtToken;
import com.sppart.admin.sub.user.domain.Users;
import com.sppart.admin.sub.user.domain.mapper.UserMapper;
import com.sppart.admin.sub.user.dto.LoginDto;
import com.sppart.admin.sub.user.dto.LoginResponse;
import com.sppart.admin.sub.user.exception.UserErrorCode;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public LoginResponse login(LoginDto dto) {
        Users findUser = userMapper.findById(dto.getLoginId())
                .orElseThrow(() -> new SuperpositionAdminException(UserErrorCode.ID_OR_PW_NOT_VALID));
        validPassword(dto.getLoginPassword(), findUser.getPassword());

        JwtToken jwtToken = jwtProvider.generateJwtToken(findUser.getId(), findUser.getRole());

        return LoginResponse.builder()
                .userInfo(findUser)
                .accessToken(jwtToken.getAccessToken())
                .message("success")
                .build();
    }

    @Override
    public void logout(HttpServletRequest request) {
        log.info("logout 요청");
    }

    private void validPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new SuperpositionAdminException(UserErrorCode.ID_OR_PW_NOT_VALID);
        }
    }
}
