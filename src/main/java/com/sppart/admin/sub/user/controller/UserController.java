package com.sppart.admin.sub.user.controller;

import com.sppart.admin.sub.user.dto.CurrentUser;
import com.sppart.admin.sub.user.dto.LoginDto;
import com.sppart.admin.sub.user.dto.LoginRequest;
import com.sppart.admin.sub.user.dto.LoginResponse;
import com.sppart.admin.sub.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "로그인 및 로그아웃")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "로그인", notes = "ID와 PASSWORD 회원 정보를 기반으로 로그인 하는 API입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "사용자 정보와 성공 메시지를 반환합니다.", response = LoginResponse.class),
            @ApiResponse(code = 400, message = "ID 또는 PASSWORD를 잘못 입력했습니다."),
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest,
                                   HttpServletRequest httpServletRequest) {
        LoginDto loginDto = LoginDto.builder()
                .loginRequest(loginRequest)
                .httpServletRequest(httpServletRequest)
                .build();
        LoginResponse loginResponse = userService.login(loginDto);
        return ResponseEntity.ok(loginResponse);
    }

    @ApiOperation(value = "로그아웃", notes = "JSESSIONID 쿠키를 전달해서 로그아웃을 하는 API입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "해당 사용자의 세션 삭제 성공."),
    })
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@ApiParam(hidden = true) @CurrentUser String id, HttpServletRequest request) {
        userService.logout(request);
    }
}
