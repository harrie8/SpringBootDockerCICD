package com.sppart.admin.main.productexhibition.domain.mapper;

import com.sppart.admin.main.productexhibition.dto.ExhibitionHistoryOfProductDto;
import com.sppart.admin.main.productexhibition.dto.ProductExhibitionDto;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductExhibitionMapper {
    int bulkDeleteByExhibitionIds(@Param("exhibitionIds") Set<Long> exhibitionIds);

    int countAll();

    void bulkInsertByExhibitionId(@Param("exhibitionId") Long exhibitionId, @Param("productIds") Set<Long> productIds);

    List<ProductExhibitionDto> findByExhibitionId(@Param("exhibitionId") Long exhibitionId);

    List<ExhibitionHistoryOfProductDto> findExhibitionHistoryOfProductByProductId(@Param("productId") Long productId);

    void deleteByExhibitionId(@Param("exhibitionId") Long exhibitionId);
}
