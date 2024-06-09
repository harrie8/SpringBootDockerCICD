package com.sppart.admin.main.artist.service;

import com.sppart.admin.main.artist.dto.RequestArtistInfo;
import com.sppart.admin.main.artist.dto.RequestUpdateArtistInfo;
import com.sppart.admin.main.artist.dto.ResponseArtistDetail;
import com.sppart.admin.main.artist.dto.ResponseArtists;
import com.sppart.admin.utils.FilterType;
import java.util.Date;
import org.springframework.http.ResponseEntity;

public interface ArtistService {
    ResponseArtists getAllArtists(Date startDate, Date endDate, boolean isUser, String searchString,
                                  FilterType filterType, int page);

    ResponseArtistDetail getArtist(String instagramId);

    ResponseEntity<?> editArtistInfo(RequestUpdateArtistInfo artistInfo);

    ResponseEntity<?> registerArtist(RequestArtistInfo artistInfo);

    void deleteArtists(String[] ids);

}
