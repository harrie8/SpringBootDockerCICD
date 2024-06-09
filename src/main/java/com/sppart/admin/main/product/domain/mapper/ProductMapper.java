package com.sppart.admin.main.product.domain.mapper;

import com.sppart.admin.main.product.domain.entity.Product;
import com.sppart.admin.main.product.dto.DetailProductInfo;
import com.sppart.admin.main.product.dto.ProductSearchCondition;
import com.sppart.admin.main.product.dto.request.RequestUpdateProduct;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper {

    List<DetailProductInfo> findDetailProductInfosByCondition(@Param("condition") ProductSearchCondition condition);

    int countAll();

    int bulkDeleteByIds(@Param("ids") Set<Long> ids);

    Optional<DetailProductInfo> findDetailProductInfoById(@Param("productId") Long productId);

    void save(Product product);

    List<DetailProductInfo> findDetailProductInfoByIds(@Param("productIds") Set<Long> productIds);

    void update(@Param("productId") Long productId, @Param("picture") String picture,
                @Param("req") RequestUpdateProduct req);
}
