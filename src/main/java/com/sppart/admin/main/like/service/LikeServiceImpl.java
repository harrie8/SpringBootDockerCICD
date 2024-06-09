package com.sppart.admin.main.like.service;

import com.sppart.admin.main.like.domain.mapper.LikeMapper;
import com.sppart.admin.main.like.dto.LikeWithUser;
import com.sppart.admin.utils.PageInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeMapper likeMapper;

    @Override
    @Transactional(readOnly = true)
    public PageInfo<LikeWithUser> getUserLikesByProductId(Pageable pageable, long productId) {
        int offset = calculateOffset(pageable);
        int limit = pageable.getPageSize();

        int totalCount = likeMapper.getTotalCountByProductId(productId);
        List<LikeWithUser> likesByProductId = likeMapper.getLikesByProductId(offset, limit, productId);

        return PageInfo.<LikeWithUser>builder()
                .pageIndex(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .totalCount(totalCount)
                .data(likesByProductId)
                .build();
    }

    // 페이지 번호는 1번부터 시작
    private int calculateOffset(Pageable pageable) {
        return (pageable.getPageNumber() - 1) * pageable.getPageSize();
    }
}
