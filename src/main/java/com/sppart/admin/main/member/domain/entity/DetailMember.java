package com.sppart.admin.main.member.domain.entity;

import com.sppart.admin.utils.Gender;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DetailMember {
    private String email;
    private Gender gender;
    private String name;
    private Date birth;
    private String profile;
    private boolean isArtist;
    private Date createAt;
    private Date updateAt;
    //추후 현재 안 됨
    private String instagramId; //인스타 ID
    private String artistName;
}
