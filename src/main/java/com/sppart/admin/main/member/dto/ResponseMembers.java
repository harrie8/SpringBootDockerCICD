package com.sppart.admin.main.member.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseMembers {
    private List<?> members;
    private int total;
}
