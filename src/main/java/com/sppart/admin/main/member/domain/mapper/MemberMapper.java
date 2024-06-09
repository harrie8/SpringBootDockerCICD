package com.sppart.admin.main.member.domain.mapper;

import com.sppart.admin.main.member.domain.entity.Member;
import com.sppart.admin.utils.Gender;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    List<Member> getAllMembers(@Param("start") int start, @Param("end") int end, @Param("isActive") boolean isActive);

    List<Member> getMembersByEmail(@Param("email") String email, @Param("start") int start, @Param("end") int end,
                                   @Param("isActive") boolean isActive);

    List<Member> getMembersByName(@Param("name") String name, @Param("start") int start, @Param("end") int end,
                                  @Param("isActive") boolean isActive);

    List<Member> getArtisMember(@Param("start") int start, @Param("end") int end, @Param("isActive") boolean isActive);

    List<Member> getArtistMembersByEmail(@Param("email") String email, @Param("start") int start, @Param("end") int end,
                                         @Param("isActive") boolean isActive);

    List<Member> getArtistMembersByName(@Param("name") String name, @Param("start") int start, @Param("end") int end,
                                        @Param("isActive") boolean isActive);

    List<Member> getMembersByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
                                  @Param("start") int start, @Param("end") int end,
                                  @Param("isActive") boolean isActive);

    List<Member> getMembersByDateAndEmail(@Param("email") String email, @Param("startDate") Date startDate,
                                          @Param("endDate") Date endDate, @Param("start") int start,
                                          @Param("end") int end, @Param("isActive") boolean isActive);

    List<Member> getMembersByDateAndName(@Param("email") String email, @Param("startDate") Date startDate,
                                         @Param("endDate") Date endDate, @Param("start") int start,
                                         @Param("end") int end, @Param("isActive") boolean isActive);

    List<Member> getArtistMembersByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
                                        @Param("start") int start, @Param("end") int end,
                                        @Param("isActive") boolean isActive);

    List<Member> getArtistMembersByDateAndEmail(@Param("email") String email, @Param("startDate") Date startDate,
                                                @Param("endDate") Date endDate, @Param("start") int start,
                                                @Param("end") int end, @Param("isActive") boolean isActive);

    List<Member> getArtistMembersByDateAndName(@Param("name") String name, @Param("startDate") Date startDate,
                                               @Param("endDate") Date endDate, @Param("start") int start,
                                               @Param("end") int end, @Param("isActive") boolean isActive);

    Member getMemberByEmail(@Param("email") String email);

    void updateUserNicknameByEmail(@Param("email") String email, @Param("nickname") String nickname);

    void updateUserGenderByEmail(@Param("email") String email, @Param("gender") Gender gender);

    void updateUserBirthByEmail(@Param("email") String email, @Param("birth") Date birth);

    void updateUserProfileByEmail(@Param("email") String email, @Param("profile") String profile);

    void deleteMemberByEmail(@Param("email") String email);

    boolean isDuplicateName(@Param("name") String name);
}
