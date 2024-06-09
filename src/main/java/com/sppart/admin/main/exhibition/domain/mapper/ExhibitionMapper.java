package com.sppart.admin.main.exhibition.domain.mapper;

import com.sppart.admin.main.exhibition.domain.entity.Exhibition;
import com.sppart.admin.main.exhibition.dto.ExhibitionByCondition;
import com.sppart.admin.main.exhibition.dto.ExhibitionSearchCondition;
import com.sppart.admin.main.exhibition.dto.ExhibitionWithParticipatedProducts;
import com.sppart.admin.main.exhibition.dto.request.RequestUpdateExhibition;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExhibitionMapper {

    List<ExhibitionByCondition> findExhibitionsByCondition(@Param("condition") ExhibitionSearchCondition condition);

    int countAll();

    List<Exhibition> findByIds(@Param("ids") Set<Long> ids);

    int bulkDeleteByIds(@Param("ids") Set<Long> ids);

    Optional<Exhibition> findById(@Param("exhibitionId") Long exhibitionId);

    void updateOnlyDisplay(@Param("exhibitionId") Long exhibitionId, @Param("isDisplay") int isDisplay);

    Optional<ExhibitionWithParticipatedProducts> findByIdWithParticipatedProducts(
            @Param("exhibitionId") Long exhibitionId);

    void save(Exhibition exhibition);

    void update(@Param("exhibitionId") Long exhibitionId, @Param("poster") String poster,
                @Param("req") RequestUpdateExhibition req);
}
