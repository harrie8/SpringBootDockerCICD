package com.sppart.admin.main.like.controller;

import com.sppart.admin.main.exhibition.dto.ResponseGetExhibitionsByCondition;
import com.sppart.admin.main.like.dto.LikeWithUser;
import com.sppart.admin.main.like.service.LikeService;
import com.sppart.admin.utils.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "좋아요")
@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    @ApiOperation(value = "작품의 좋아요 내역 조회", notes = "작품 ID로 좋아요 내역을 조회하는 하는 API입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공.", response = ResponseGetExhibitionsByCondition.class),
    })
    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public PageInfo<LikeWithUser> getUserLikesByProductId(
            @ApiParam(value = "페이징 정보") @PageableDefault(page = 1, size = 10) Pageable pageable,
            @ApiParam(value = "조회하고 싶은 작품 ID") @PathVariable long productId) {
        return likeService.getUserLikesByProductId(pageable, productId);
    }
}
