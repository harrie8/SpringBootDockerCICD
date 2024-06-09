package com.sppart.admin.main.artist.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class RequestUpdateArtistInfo {
    private String id;
    private String name;
    private String message;
    private String introduce;
    private MultipartFile image;
    private String userAccount;
}
