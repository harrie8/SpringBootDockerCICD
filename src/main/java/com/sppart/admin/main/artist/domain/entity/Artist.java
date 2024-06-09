package com.sppart.admin.main.artist.domain.entity;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Artist {
    private String name;
    private String profile;
    private String introduce;
    private String description;
    private String instagramId;
    private boolean isDisplay;
    private int view;
    private boolean isAbout;
    private Date collaborationDate;
}
