package com.sppart.admin.main.product.controller;

import com.sppart.admin.main.exhibition.dto.ResponseGetExhibitionsByCondition;
import com.sppart.admin.main.product.dto.ProductSearchCondition;
import com.sppart.admin.main.product.dto.request.RequestCreateProduct;
import com.sppart.admin.main.product.dto.request.RequestGetProducts;
import com.sppart.admin.main.product.dto.request.RequestUpdateProduct;
import com.sppart.admin.main.product.dto.response.ResponseBulkDeleteProductByIds;
import com.sppart.admin.main.product.dto.response.ResponseDetailProductInfo;
import com.sppart.admin.main.product.dto.response.ResponseGetProductsByCondition;
import com.sppart.admin.main.product.dto.response.ResponsePaging;
import com.sppart.admin.main.product.service.ProductService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "작품")
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @ApiOperation(value = "작품 목록 - 검색 조건으로 작품 목록 조회", notes = "작품명, 작가명, 작품 코드 3가지 검색 조건으로 작품 목록을 조회하는 하는 API입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "검색 조건으로 조회한 작품 목록을 반환합니다.", response = ResponseGetExhibitionsByCondition.class),
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponsePaging<ResponseGetProductsByCondition> getExhibitionsByCondition(
            @ModelAttribute RequestGetProducts req) {
        ProductSearchCondition condition = ProductSearchCondition.builder()
                .title(req.getTitle())
                .artistName(req.getArtistName())
                .productId(req.getProductId())
                .build();

        return productService.getProductsByCondition(condition);
    }

    @ApiOperation(value = "작품 목록 - 선택 삭제", notes = "삭제하고 싶은 작품 코드들로 작품들을 삭제하는 API입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "작품 삭제 성공 - 삭제 성공한 작품의 개수를 반환합니다.", response = String.class),
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> bulkDeleteByIds(@ApiParam(value = "삭제하고 싶은 작품 id들") @RequestParam Set<Long> ids) {
        ResponseBulkDeleteProductByIds deleteCount = productService.bulkDeleteByIds(ids);

        return ResponseEntity.ok(deleteCount.toString());
    }

    @ApiOperation(value = "작품 상세 - 작품 상세 조회", notes = "작품의 상세 정보와 태그 등을 조회하는 API입니다. - 좋아요 내역은 작품의 좋아요 조회 API를 사용해주세요.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공", response = String.class),
            @ApiResponse(code = 404, message = "존재하지 않는 작품", response = String.class),
    })
    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDetailProductInfo getDetailProductInfoById(@ApiParam(value = "작품 ID") @PathVariable Long productId) {

        return productService.getDetailInfoById(productId);
    }

    @ApiOperation(value = "작품 등록 - 신규 작품 등록", notes = "작품 정보 및 작품 이미지를 생성하는 API입니다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "생성 성공 - 신규 전시의 노출 여부는 `0`입니다."),
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@Valid @RequestPart RequestCreateProduct req,
                              @ApiParam(value = "작품 이미지 파일") @RequestPart MultipartFile picture) {

        productService.create(req, picture);
    }

    @ApiOperation(value = "작품 수정", notes = "작품의 정보룰 수정하는 API입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "작품 수정 성공", response = String.class)
    })
    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public String updateProduct(@ApiParam(value = "작품 ID") @PathVariable Long productId,
                                @Valid @RequestPart RequestUpdateProduct req,
                                @ApiParam(value = "작품 이미지 파일") @RequestPart(required = false) MultipartFile picture) {
        productService.update(productId, req, picture);

        return "product update success";
    }
}
