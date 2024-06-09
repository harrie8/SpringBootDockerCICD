package com.sppart.admin.main.product.service;

import com.sppart.admin.main.product.dto.ProductSearchCondition;
import com.sppart.admin.main.product.dto.request.RequestCreateProduct;
import com.sppart.admin.main.product.dto.request.RequestUpdateProduct;
import com.sppart.admin.main.product.dto.response.ResponseBulkDeleteProductByIds;
import com.sppart.admin.main.product.dto.response.ResponseDetailProductInfo;
import com.sppart.admin.main.product.dto.response.ResponseGetProductsByCondition;
import com.sppart.admin.main.product.dto.response.ResponsePaging;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    ResponsePaging<ResponseGetProductsByCondition> getProductsByCondition(ProductSearchCondition condition);

    ResponseBulkDeleteProductByIds bulkDeleteByIds(Set<Long> ids);

    ResponseDetailProductInfo getDetailInfoById(Long productId);

    long create(RequestCreateProduct req, MultipartFile picture);

    void update(Long productId, RequestUpdateProduct req, MultipartFile picture);
}
