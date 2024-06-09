package com.sppart.admin.main.artist.controller;

import com.sppart.admin.main.artist.dto.RequestArtistInfo;
import com.sppart.admin.main.artist.dto.RequestUpdateArtistInfo;
import com.sppart.admin.main.artist.service.ArtistService;
import com.sppart.admin.utils.FilterType;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artists")
public class ArtistController {
    private final ArtistService artistService;

    @GetMapping
    public ResponseEntity<?> getAllArtists(@RequestParam(value = "startDate", required = false) Date startDate,
                                           @RequestParam(value = "endDate", required = false) Date endDate,
                                           @RequestParam(value = "isUser", defaultValue = "false") boolean isUser,
                                           @RequestParam(value = "searchString", required = false) String searchString,
                                           @RequestParam(value = "filterType", defaultValue = "all") FilterType filterType,
                                           @RequestParam(value = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok(
                artistService.getAllArtists(startDate, endDate, isUser, searchString, filterType, page));
    }

    @GetMapping("/one")
    public ResponseEntity<?> getArtist(@RequestParam(value = "id") String id) {
        return ResponseEntity.ok(artistService.getArtist(id));
    }

    @PutMapping
    public ResponseEntity<?> editArtistInfo(@RequestBody RequestUpdateArtistInfo artistInfo) {
        return artistService.editArtistInfo(artistInfo);
    }

    @PostMapping
    public ResponseEntity<?> registerArtist(@RequestBody RequestArtistInfo artistInfo) {
        return artistService.registerArtist(artistInfo);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteArtists(@RequestParam(value = "id") String[] ids) {
        artistService.deleteArtists(ids);
    }
}
