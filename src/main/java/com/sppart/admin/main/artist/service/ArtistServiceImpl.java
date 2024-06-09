package com.sppart.admin.main.artist.service;

import com.sppart.admin.main.artist.domain.entity.Artist;
import com.sppart.admin.main.artist.domain.mapper.ArtistMapper;
import com.sppart.admin.main.artist.dto.Artists;
import com.sppart.admin.main.artist.dto.RequestArtistInfo;
import com.sppart.admin.main.artist.dto.RequestUpdateArtistInfo;
import com.sppart.admin.main.artist.dto.ResponseArtistDetail;
import com.sppart.admin.main.artist.dto.ResponseArtists;
import com.sppart.admin.objectstorage.service.ObjectStorageService;
import com.sppart.admin.utils.FilterType;
import com.sppart.admin.utils.Page;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {
    private final ArtistMapper artistMapper;
    private final ObjectStorageService objectStorageService;

    @Override
    @Transactional
    public ResponseArtists getAllArtists(Date startDate, Date endDate, boolean isUser, String searchString,
                                         FilterType filterType, int page) {
        List<?> artists = getArtistListByFilter(filterType, searchString, page, startDate, endDate, isUser);
        return ResponseArtists.builder().artists(artists).total(artists.size()).build();
    }

    @Override
    public ResponseArtistDetail getArtist(String instagramId) {
        return new ResponseArtistDetail(artistMapper.getArtistByInstagramId(instagramId));
    }

    @Override
    @Transactional
    public ResponseEntity<?> editArtistInfo(RequestUpdateArtistInfo artistInfo) {
        String instagramId = artistInfo.getId();

        if (artistMapper.isExistArtist(instagramId, artistInfo.getName())) {
            if (Objects.nonNull(artistInfo.getName())) {
                artistMapper.updateNameByInstagramId(instagramId, artistInfo.getName());
            }

            if (Objects.nonNull(artistInfo.getMessage())) {
                artistMapper.updateIntroduceByInstagramId(instagramId, artistInfo.getMessage());
            }

            if (Objects.nonNull(artistInfo.getIntroduce())) {
                artistMapper.updateDescriptionByInstagramId(instagramId, artistInfo.getIntroduce());
            }

            if (Objects.nonNull(artistInfo.getImage())) {
                artistMapper.updateProfileByInstagramId(instagramId, getProfileId(artistInfo.getImage()));
            }

            return ResponseEntity.ok("Edit Artist Information Successfully");
        } else {
            return ResponseEntity.badRequest().body("It's not our artist information.");
        }
        //TODO : 나중에 회원 계정 연동 후 추가 및 구현 필요
//        if (Objects.nonNull(artistInfo.getUserAccount())) {
//
//        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> registerArtist(RequestArtistInfo artistInfo) {
        if (artistMapper.isExistArtist(artistInfo.getId(), artistInfo.getName())) {
            return ResponseEntity.badRequest().body("Already Exists Artist.");
        } else {
            artistMapper.insertArtist(toEntity(artistInfo));
            return ResponseEntity.ok("Success.");
        }
    }

    @Override
    @Transactional
    public void deleteArtists(String[] ids) {
        Arrays.stream(ids).forEach(artistMapper::deleteArtist);
    }

    @Transactional
    public List<?> getArtistListByFilter(FilterType filterType,
                                         String searchString,
                                         int page,
                                         Date startDate, Date endDate,
                                         boolean isUser) {
        int[] pageIndex = Page.getPageIndex(page);

        switch (filterType) {
            case all -> {
                return handleAllFilter(startDate, endDate, pageIndex, isUser);
            }

            case id -> {
                return handleIdFilter(searchString, startDate, endDate, pageIndex, isUser);
            }

            case name -> {
                return handleNameFilter(searchString, startDate, endDate, pageIndex, isUser);
            }

            default -> {
                log.error("[{}] Parsing Error.. Wrong Filter Type.", getClass().getSimpleName());
                throw new RuntimeException();
            }
        }
    }

    //TODO : isUser 분기 처리 구현 필요
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public List<?> handleAllFilter(Date startDate, Date endDate, int[] pageIndex, boolean isUser) {
        if (Objects.nonNull(startDate)) {
            return entityToResponse(artistMapper.getArtistsByDate(startDate, endDate, pageIndex[0], pageIndex[1]));
        } else {
            return entityToResponse(artistMapper.getAllArtists(pageIndex[0], pageIndex[1]));
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public List<?> handleIdFilter(String searchString, Date startDate, Date endDate, int[] pageIndex, boolean isUser) {
        if (Objects.nonNull(startDate)) {
            return entityToResponse(
                    artistMapper.getArtistsByDateAndInstagramId(searchString, startDate, endDate, pageIndex[0],
                            pageIndex[1]));
        } else {
            return entityToResponse(artistMapper.getArtistsByInstagramId(searchString, pageIndex[0], pageIndex[1]));
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public List<?> handleNameFilter(String searchString, Date startDate, Date endDate, int[] pageIndex,
                                    boolean isUser) {
        if (Objects.nonNull(startDate)) {
            return entityToResponse(
                    artistMapper.getArtistsByDateAndName(searchString, startDate, endDate, pageIndex[0], pageIndex[1]));
        } else {
            return entityToResponse(artistMapper.getArtistsByName(searchString, pageIndex[0], pageIndex[1]));
        }
    }

    private List<Artists> entityToResponse(List<Artist> artists) {
        List<Artists> artistsList = new ArrayList<>(artists.size());
        artists.forEach(artist -> artistsList.add(
                Artists.builder()
                        .instagramId(artist.getInstagramId())
                        .name(artist.getName())
                        .isUser(false)
                        .collaborationDate(artist.getCollaborationDate()).build()));
        return artistsList;
    }

    private Artist toEntity(RequestArtistInfo artistInfo) {
        return Artist.builder()
                .name(artistInfo.getName())
                .profile(getProfileId(artistInfo.getImage()))
                .introduce(artistInfo.getMessage())
                .description(artistInfo.getIntroduce())
                .isDisplay(false)
                .collaborationDate(artistInfo.getCollaborationDate()).build();
    }

    private String getProfileId(MultipartFile imageFile) {
        if (Objects.isNull(imageFile)) {
            return null;
        }
        return objectStorageService.uploadFile(imageFile);
    }
}
