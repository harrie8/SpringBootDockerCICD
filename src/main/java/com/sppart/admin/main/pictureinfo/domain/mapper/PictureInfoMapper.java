package com.sppart.admin.main.pictureinfo.domain.mapper;

import com.sppart.admin.main.pictureinfo.dto.RequestCreatePictureInfo;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PictureInfoMapper {

    void saveBy(@Param("productId") Long productId, @Param("req") RequestCreatePictureInfo req);

    int bulkDeleteByProductIds(@Param("productIds") Set<Long> productIds);

    void deleteByProductId(@Param("productId") Long productId);
}
