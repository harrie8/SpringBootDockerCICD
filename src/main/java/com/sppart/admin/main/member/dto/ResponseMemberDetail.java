package com.sppart.admin.main.member.dto;

import com.sppart.admin.main.member.domain.entity.Member;
import com.sppart.admin.utils.Gender;
import java.sql.Timestamp;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseMemberDetail {
    private int num;
    private String email;
    private Gender gender;
    private int birth;
    private String image;
    private String userType;
    private Timestamp createAt;
    private Date updateAt;
    //추후 추가 예정
//    private AuthorInfo authorInfo;

    @Builder
    public ResponseMemberDetail(Member member) {
        this.num = 1;
        this.email = member.getEmail();
        this.gender = member.getGender();
        this.birth = member.getBirthYear();
        this.image = member.getProfile();
        if (member.isArtist()) {
            this.userType = "artist";
        } else {
            this.userType = "user";
        }
        this.createAt = member.getCreateAt();
        this.updateAt = member.getNicknameUpdateAt();
        //추후 추가 예정
//        this.authorInfo = AuthorInfo.builder()
//                .id(member.getInstagramId())
//                .name(member.getArtistName()).build();
    }
}
