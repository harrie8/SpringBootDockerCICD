package com.sppart.admin.main.exhibition.controller;

import com.sppart.admin.main.exhibition.dto.ExhibitionSearchCondition;
import com.sppart.admin.main.exhibition.dto.ExhibitionWithParticipatedProducts;
import com.sppart.admin.main.exhibition.dto.RequestUpdateExhibitionDisplay;
import com.sppart.admin.main.exhibition.dto.ResponseBulkDeleteByIds;
import com.sppart.admin.main.exhibition.dto.ResponseGetExhibitionsByCondition;
import com.sppart.admin.main.exhibition.dto.request.RequestCreateExhibition;
import com.sppart.admin.main.exhibition.dto.request.RequestGetExhibitions;
import com.sppart.admin.main.exhibition.dto.request.RequestUpdateExhibition;
import com.sppart.admin.main.exhibition.dto.response.ResponseExhibitionWithParticipatedProducts;
import com.sppart.admin.main.exhibition.service.ExhibitionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "전시")
@RestController
@RequiredArgsConstructor
@RequestMapping("/exhibitions")
public class ExhibitionController {

    private final ExhibitionService exhibitionService;

    @ApiOperation(value = "전시 목록 - 검색 조건으로 전시 목록", notes = "시작일, 종료일, 전시 제목, 작가 이름 4가지 검색 조건을 기반으로 전시 목록을 조회하는 하는 API입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "사용자 정보와 성공 메시지를 반환합니다.", response = ResponseGetExhibitionsByCondition.class),
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseGetExhibitionsByCondition getExhibitionsByCondition(@ModelAttribute RequestGetExhibitions req) {
        ExhibitionSearchCondition condition = ExhibitionSearchCondition.builder()
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .title(req.getTitle())
                .artistName(req.getArtistName())
                .build();

        return exhibitionService.getExhibitionsByCondition(condition);
    }

    @ApiOperation(value = "전시 목록 - 선택 삭제", notes = "삭제하고 싶은 전시 번호들로 전시들과 해당 전시에 참여한 작품 이력도 삭제하는 API입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "전시 삭제 성공 - 삭제 성공한 전시의 개수와 작품 참여 이력 개수를 반환합니다.", response = String.class),
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> bulkDeleteByIds(@ApiParam(value = "삭제하고 싶은 전시 id들") @RequestParam Set<Long> ids) {
        ResponseBulkDeleteByIds deleteCount = exhibitionService.bulkDeleteByIds(ids);

        return ResponseEntity.ok(deleteCount.toString());
    }

    @ApiOperation(value = "전시 목록 - 노출 여부 변경", notes = "전시의 노출 여부를 변경하는 API입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "전시 노출 여부 변경 성공", response = String.class),
            @ApiResponse(code = 404, message = "존재하지 않는 전시", response = String.class),
    })
    @PatchMapping("/{exhibitionId}")
    @ResponseStatus(HttpStatus.OK)
    public String updateDisplay(@ApiParam(value = "전시 ID") @PathVariable Long exhibitionId,
                                @Valid @RequestBody RequestUpdateExhibitionDisplay req) {
        exhibitionService.updateOnlyDisplay(exhibitionId, req);

        return "display update success";
    }

    @ApiOperation(value = "전시 상세 - 전시 상세 조회", notes = "전시 정보와 해당 전시에 포함된 작품들 정보도 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공", response = String.class),
            @ApiResponse(code = 404, message = "존재하지 않는 전시", response = String.class),
    })
    @GetMapping("/{exhibitionId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseExhibitionWithParticipatedProducts getByIdWithParticipatedProducts(
            @ApiParam(value = "전시 ID") @PathVariable Long exhibitionId) {

        ExhibitionWithParticipatedProducts result = exhibitionService.getByIdWithParticipatedProducts(
                exhibitionId);
        return ResponseExhibitionWithParticipatedProducts.from(result);
    }

    @ApiOperation(value = "전시 등록 - 신규 전시 등록", notes = "전시 정보 및 전시 포스터와 해당 전시에 포함된 작품들을 생성하는 API입니다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "생성 성공 - 신규 전시의 노출 여부는 `0`입니다."),
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public void createExhibition(@Valid @RequestPart RequestCreateExhibition req,
                                 @ApiParam(value = "포스터 이미지 파일") @RequestPart MultipartFile poster) {
        exhibitionService.create(req, poster);
    }

    @ApiOperation(value = "전시 수정", notes = "전시의 정보룰 수정하는 API입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "전시 수정 성공", response = String.class)
    })
    @PutMapping("/{exhibitionId}")
    @ResponseStatus(HttpStatus.OK)
    public String updateExhibition(@ApiParam(value = "작품 ID") @PathVariable Long exhibitionId,
                                   @Valid @RequestPart RequestUpdateExhibition req,
                                   @ApiParam(value = "전시 포스터 파일") @RequestPart(required = false) MultipartFile poster) {
        exhibitionService.update(exhibitionId, req, poster);

        return "exhibition update success";
    }
}
