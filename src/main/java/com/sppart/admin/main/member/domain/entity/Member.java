package com.sppart.admin.main.member.domain.entity;

import com.sppart.admin.utils.Gender;
import java.sql.Date;
import java.sql.Timestamp;
import lombok.Getter;

@Getter
public class Member {
    private String email;
    private String name;
    private String nickname;
    private String profile;
    private Gender gender;
    private int birthYear;
    private boolean artist;
    private Timestamp createAt;
    private Timestamp deleteAt;
    private Date nicknameUpdateAt;
    private boolean isActive;
}
