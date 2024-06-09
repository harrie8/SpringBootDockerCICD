package com.sppart.admin.main.tag.service;

import com.sppart.admin.main.tag.dto.request.RequestCreateTag;
import com.sppart.admin.main.tag.dto.response.ResponseAllTags;

public interface TagService {

    ResponseAllTags getAllTags();

    void create(RequestCreateTag req);
}
