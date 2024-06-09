package com.sppart.admin.main.member.dto;

import java.sql.Timestamp;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Members {
    private int num;
    private String email;
    private String name;
    private boolean isAuthor;
    private Timestamp createAt;
    private Date updateAt;
}
