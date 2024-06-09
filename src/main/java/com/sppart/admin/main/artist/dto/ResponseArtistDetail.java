package com.sppart.admin.main.artist.dto;

import com.sppart.admin.main.artist.domain.entity.Artist;
import java.util.Date;
import lombok.Getter;

@Getter
public class ResponseArtistDetail {
    private final String instaId;
    private final String name;
    private final String message;
    private final String introduce;
    private final String image;
    private final boolean isUser;
    private final Date collaborationDate;

    public ResponseArtistDetail(Artist artistInfo) {
        this.instaId = artistInfo.getInstagramId();
        this.name = artistInfo.getName();
        this.message = artistInfo.getIntroduce();
        this.introduce = artistInfo.getDescription();
        this.image = artistInfo.getProfile();
        this.isUser = false;
        this.collaborationDate = artistInfo.getCollaborationDate();
    }
}
