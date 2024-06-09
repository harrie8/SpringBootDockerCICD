package com.sppart.admin.main.member.dto;

import com.sppart.admin.utils.Gender;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class UpdateUserInfo {
    private String email;
    private String nickname;
    private Gender gender;
    private Date birth;
    private MultipartFile image;
}
