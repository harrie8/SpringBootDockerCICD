package com.sppart.admin.main.artist.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class RequestArtistInfo {
    private String id;
    private String name;
    private String message;
    private String introduce;
    private MultipartFile image;
    private Date collaborationDate;
}
