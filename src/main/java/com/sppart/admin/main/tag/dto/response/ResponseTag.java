package com.sppart.admin.main.tag.dto.response;

import com.sppart.admin.main.tag.domain.entity.Tag;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseTag {

    private Long tagId;
    private String name;

    @Builder
    public ResponseTag(Long tagId, String name) {
        this.tagId = tagId;
        this.name = name;
    }

    public static ResponseTag from(Tag tag) {
        return ResponseTag.builder()
                .tagId(tag.getTag_id())
                .name(tag.getName())
                .build();
    }
}
