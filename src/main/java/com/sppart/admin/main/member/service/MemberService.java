package com.sppart.admin.main.member.service;

import com.sppart.admin.main.member.dto.ResponseMemberDetail;
import com.sppart.admin.main.member.dto.ResponseMembers;
import com.sppart.admin.main.member.dto.UpdateUserInfo;
import com.sppart.admin.utils.FilterType;
import java.util.Date;
import org.springframework.http.ResponseEntity;

public interface MemberService {
    ResponseMembers getAllMembers(Date startDate, Date endDate, boolean isAuthor, String searchString,
                                  FilterType filterType, int page);

    ResponseMemberDetail getMemberByEmail(String email);

    ResponseMembers getDeleteMembers(Date startDate, Date endDate, boolean isAuthor, String searchString,
                                     FilterType filterType, int page);

    ResponseEntity<?> editMemberInfo(UpdateUserInfo userInfo);

    void deleteMemberByEmail(String[] email);

    boolean isDuplicateName(String name);
}
