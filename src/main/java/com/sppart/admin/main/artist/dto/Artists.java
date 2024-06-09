package com.sppart.admin.main.artist.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Artists {
    private String instagramId;
    private String name;
    private boolean isUser;
    private Date collaborationDate;
}
