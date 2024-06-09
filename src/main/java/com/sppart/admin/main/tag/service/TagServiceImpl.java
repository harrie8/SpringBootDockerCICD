package com.sppart.admin.main.tag.service;

import com.sppart.admin.exception.SuperpositionAdminException;
import com.sppart.admin.main.tag.domain.entity.Tag;
import com.sppart.admin.main.tag.domain.mapper.TagMapper;
import com.sppart.admin.main.tag.dto.request.RequestCreateTag;
import com.sppart.admin.main.tag.dto.response.ResponseAllTags;
import com.sppart.admin.main.tag.dto.response.ResponseTag;
import com.sppart.admin.main.tag.exception.TagErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;

    @Override
    @Transactional(readOnly = true)
    public ResponseAllTags getAllTags() {
        List<Tag> tags = tagMapper.findAll();

        List<ResponseTag> responseTags = tags.stream()
                .map(ResponseTag::from)
                .toList();
        return new ResponseAllTags(responseTags);
    }

    @Override
    public void create(RequestCreateTag req) {
        req.validateName();

        Tag tag = Tag.builder()
                .name(req.getName())
                .build();
        try {
            tagMapper.save(tag);
        } catch (DuplicateKeyException e) {
            throw new SuperpositionAdminException(TagErrorCode.DUPLICATE_NAME);
        }
    }
}
