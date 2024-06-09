package com.sppart.admin.main.artist.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseArtists {
    List<?> artists;
    int total;
}
