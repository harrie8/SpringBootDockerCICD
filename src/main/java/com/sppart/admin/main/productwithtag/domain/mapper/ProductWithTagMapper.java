package com.sppart.admin.main.productwithtag.domain.mapper;

import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductWithTagMapper {

    int bulkDeleteByProductIds(@Param("ids") Set<Long> ids);

    void saveBy(@Param("productId") Long productId, @Param("tagIds") Set<Long> tagIds);

    void deleteByProductId(@Param("productId") Long productId);
}
