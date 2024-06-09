package com.sppart.admin.main.product.service;

import static java.util.stream.Collectors.groupingBy;

import com.sppart.admin.exception.SuperpositionAdminException;
import com.sppart.admin.main.exhibition.domain.entity.ExhibitionStatus;
import com.sppart.admin.main.pictureinfo.domain.mapper.PictureInfoMapper;
import com.sppart.admin.main.product.domain.entity.Product;
import com.sppart.admin.main.product.domain.mapper.ProductMapper;
import com.sppart.admin.main.product.dto.DetailProductInfo;
import com.sppart.admin.main.product.dto.ProductSearchCondition;
import com.sppart.admin.main.product.dto.request.RequestCreateProduct;
import com.sppart.admin.main.product.dto.request.RequestUpdateProduct;
import com.sppart.admin.main.product.dto.response.ResponseBulkDeleteProductByIds;
import com.sppart.admin.main.product.dto.response.ResponseDetailProductInfo;
import com.sppart.admin.main.product.dto.response.ResponseGetProductsByCondition;
import com.sppart.admin.main.product.dto.response.ResponsePaging;
import com.sppart.admin.main.product.exception.ProductErrorCode;
import com.sppart.admin.main.productexhibition.domain.mapper.ProductExhibitionMapper;
import com.sppart.admin.main.productexhibition.dto.ExhibitionHistoryOfProductDto;
import com.sppart.admin.main.productwithtag.domain.mapper.ProductWithTagMapper;
import com.sppart.admin.main.tag.domain.entity.Tags;
import com.sppart.admin.main.tag.domain.mapper.TagMapper;
import com.sppart.admin.objectstorage.service.ObjectStorageService;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ObjectStorageService objectStorageService;
    private final PictureInfoMapper pictureInfoMapper;
    private final ProductWithTagMapper productWithTagMapper;
    private final TagMapper tagMapper;
    private final ProductExhibitionMapper productExhibitionMapper;

    @Override
    @Transactional(readOnly = true)
    public ResponsePaging<ResponseGetProductsByCondition> getProductsByCondition(
            ProductSearchCondition condition) {
        int totalProductCount = productMapper.countAll();
        List<DetailProductInfo> findDetailProductInfos = productMapper.findDetailProductInfosByCondition(condition);

        return toResponse(totalProductCount, findDetailProductInfos);
    }

    private ResponsePaging<ResponseGetProductsByCondition> toResponse(int totalCount,
                                                                      List<DetailProductInfo> detailProductInfos) {
        List<ResponseGetProductsByCondition> responseDetailProductInfos = detailProductInfos.stream()
                .map(ResponseGetProductsByCondition::of)
                .toList();

        return ResponsePaging.<ResponseGetProductsByCondition>builder()
                .totalCount(totalCount)
                .findResultCount(responseDetailProductInfos.size())
                .findDomains(responseDetailProductInfos)
                .build();
    }

    @Override
    @Transactional
    public ResponseBulkDeleteProductByIds bulkDeleteByIds(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResponseBulkDeleteProductByIds.zero();
        }

        deletePictures(ids);

        int productWithTagDeleteCount = productWithTagMapper.bulkDeleteByProductIds(ids);
        int pictureInfoDeleteCount = pictureInfoMapper.bulkDeleteByProductIds(ids);
        int productDeleteCount = productMapper.bulkDeleteByIds(ids);
        return ResponseBulkDeleteProductByIds.builder()
                .productDeleteCount(productDeleteCount)
                .productWithTagDeleteCount(productWithTagDeleteCount)
                .pictureInfoDeleteCount(pictureInfoDeleteCount)
                .build();
    }

    private void deletePictures(Set<Long> ids) {
        List<DetailProductInfo> findProducts = productMapper.findDetailProductInfoByIds(ids);
        if (findProducts == null || findProducts.isEmpty()) {
            return;
        }

        List<String> pictureUrls = findProducts.stream()
                .map(DetailProductInfo::getPicture)
                .toList();
        objectStorageService.delete(pictureUrls);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDetailProductInfo getDetailInfoById(Long productId) {
        DetailProductInfo findDetailProductInfo = productMapper.findDetailProductInfoById(productId)
                .orElseThrow(() -> new SuperpositionAdminException(ProductErrorCode.NOT_FOUND));
        Map<ExhibitionStatus, List<ExhibitionHistoryOfProductDto>> exhibitionHistory = getExhibitionHistory(
                findDetailProductInfo);

        return ResponseDetailProductInfo.from(findDetailProductInfo, exhibitionHistory);
    }

    private Map<ExhibitionStatus, List<ExhibitionHistoryOfProductDto>> getExhibitionHistory(
            DetailProductInfo findDetailProductInfo) {
        List<ExhibitionHistoryOfProductDto> findExhibitionHistoryOfProduct = productExhibitionMapper.findExhibitionHistoryOfProductByProductId(
                findDetailProductInfo.getProductId());
        return findExhibitionHistoryOfProduct.stream()
                .collect(groupingBy(ExhibitionHistoryOfProductDto::getExhibitionStatus));
    }

    @Override
    @Transactional
    public long create(RequestCreateProduct req, MultipartFile picture) {
        req.validateTags();

        String uuidFileName = objectStorageService.uploadFile(picture);

        Product product = Product.create(uuidFileName, req);
        productMapper.save(product);

        Long productId = product.getProduct_id();
        pictureInfoMapper.saveBy(productId, req.getPictureInfo());

        // todo cache 적용해보기, name에 unique 걸어서 동시 생성할 때 오류 나오게 수정해보기
        Tags tags = new Tags(tagMapper.findByIds(req.getTagIds()));
        productWithTagMapper.saveBy(productId, tags.getIds());

        return productId;
    }

    @Override
    @Transactional
    public void update(Long productId, RequestUpdateProduct req, MultipartFile picture) {
        req.validateTags();

        String pictureURL = getPictureURL(req, picture);
        productMapper.update(productId, pictureURL, req);

        pictureInfoMapper.deleteByProductId(productId);
        pictureInfoMapper.saveBy(productId, req.getPictureInfo());

        productWithTagMapper.deleteByProductId(productId);
        Tags tags = new Tags(tagMapper.findByIds(req.getTagIds()));
        // todo cache 적용해보기, name에 unique 걸어서 동시 생성할 때 오류 나오게 수정해보기
        productWithTagMapper.saveBy(productId, tags.getIds());
    }

    private String getPictureURL(RequestUpdateProduct req, MultipartFile picture) {
        if (picture != null && !picture.isEmpty()) {
            objectStorageService.delete(req.getOldPicture());
            return objectStorageService.uploadFile(picture);
        }
        return req.getOldPicture();
    }
}
