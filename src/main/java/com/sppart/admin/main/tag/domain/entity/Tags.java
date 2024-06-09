package com.sppart.admin.main.tag.domain.entity;

import com.sppart.admin.exception.SuperpositionAdminException;
import com.sppart.admin.main.productwithtag.exception.ProductWithTagErrorCode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Tags {

    private static final int NAME_LENGTH_SUM_MAX = 11;

    private final Set<Tag> tags;

    @Builder
    public Tags(List<Tag> tags) {
        Integer tagLengthSum = tags.stream()
                .map(Tag::getName)
                .map(String::length)
                .reduce(0, Integer::sum);
        if (tagLengthSum > NAME_LENGTH_SUM_MAX) {
            throw new SuperpositionAdminException(ProductWithTagErrorCode.NAME_LENGTH_SUM_OVER);
        }
        this.tags = new HashSet<>(tags);
    }

    public Set<Long> getIds() {
        return tags.stream()
                .map(Tag::getTag_id)
                .collect(Collectors.toSet());
    }
}
