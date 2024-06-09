package com.sppart.admin.main.like.service;

import com.sppart.admin.main.like.dto.LikeWithUser;
import com.sppart.admin.utils.PageInfo;
import org.springframework.data.domain.Pageable;

public interface LikeService {

    PageInfo<LikeWithUser> getUserLikesByProductId(final Pageable pageable, long productId);
}
