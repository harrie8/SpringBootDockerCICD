package com.sppart.admin.main.artist.domain.mapper;

import com.sppart.admin.main.artist.domain.entity.Artist;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArtistMapper {
    boolean isExistArtist(@Param("instagramId") String instagramId, @Param("name") String name);

    Artist getArtistByInstagramId(@Param("instagramId") String instagramId);

    List<Artist> getArtistsByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
                                  @Param("start") int start, @Param("end") int end);

    List<Artist> getAllArtists(@Param("start") int start, @Param("end") int end);

    List<Artist> getArtistsByDateAndInstagramId(@Param("instagramId") String instagramId,
                                                @Param("startDate") Date startDate, @Param("endDate") Date endDate,
                                                @Param("start") int start, @Param("end") int end);

    List<Artist> getArtistsByInstagramId(@Param("instagramId") String instagramId, @Param("start") int start,
                                         @Param("end") int end);

    List<Artist> getArtistsByDateAndName(@Param("name") String name, @Param("startDate") Date startDate,
                                         @Param("endDate") Date endDate, @Param("start") int start,
                                         @Param("end") int end);

    List<Artist> getArtistsByName(@Param("name") String name, @Param("start") int start, @Param("end") int end);

    void updateNameByInstagramId(@Param("instagramId") String instagramId, @Param("name") String name);

    void updateIntroduceByInstagramId(@Param("instagramId") String instagramId, @Param("introduce") String introduce);

    void updateDescriptionByInstagramId(@Param("instagramId") String instagramId,
                                        @Param("description") String description);

    void updateProfileByInstagramId(@Param("instagramId") String instagramId, @Param("profile") String profile);

    void insertArtist(Artist artist);

    void deleteArtist(@Param("instagramId") String instagramId);
}
