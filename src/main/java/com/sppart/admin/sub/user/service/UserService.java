package com.sppart.admin.sub.user.service;

import com.sppart.admin.sub.user.dto.LoginDto;
import com.sppart.admin.sub.user.dto.LoginResponse;
import javax.servlet.http.HttpServletRequest;

public interface UserService {

    LoginResponse login(LoginDto loginDto);

    void logout(HttpServletRequest request);
}
