package com.sppart.admin.main.member.service;

import com.sppart.admin.main.member.domain.entity.Member;
import com.sppart.admin.main.member.domain.mapper.MemberMapper;
import com.sppart.admin.main.member.dto.Members;
import com.sppart.admin.main.member.dto.ResponseMemberDetail;
import com.sppart.admin.main.member.dto.ResponseMembers;
import com.sppart.admin.main.member.dto.UpdateUserInfo;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    private final ObjectStorageService objectStorageService;

    @Override
    @Transactional
    public ResponseMembers getAllMembers(Date startDate, Date endDate,
                                         boolean isAuthor,
                                         String searchString,
                                         FilterType filterType,
                                         int page) {
        //Get Member Info
        List<?> members =
                getMemberListByFilter(filterType, searchString, page, startDate, endDate, isAuthor, true);

        return ResponseMembers.builder()
                .members(members)
                .total(members.size()).build();
    }

    @Override
    public ResponseMemberDetail getMemberByEmail(String email) {
        return new ResponseMemberDetail(memberMapper.getMemberByEmail(email));
    }

    @Override
    @Transactional
    public ResponseMembers getDeleteMembers(Date startDate, Date endDate,
                                            boolean isAuthor,
                                            String searchString,
                                            FilterType filterType,
                                            int page) {
        //Get Member Info
        List<?> members =
                getMemberListByFilter(filterType, searchString, page, startDate, endDate, isAuthor, false);

        return ResponseMembers.builder()
                .members(members)
                .total(members.size()).build();
    }

    @Override
    @Transactional
    public ResponseEntity<?> editMemberInfo(UpdateUserInfo userInfo) {
        String userEmail = userInfo.getEmail();
        if (Objects.nonNull(userInfo.getNickname())) {
            memberMapper.updateUserNicknameByEmail(userEmail, userInfo.getNickname());
        }

        if (Objects.nonNull(userInfo.getGender())) {
            memberMapper.updateUserGenderByEmail(userEmail, userInfo.getGender());
        }

        if (Objects.nonNull(userInfo.getBirth())) {
            memberMapper.updateUserBirthByEmail(userEmail, userInfo.getBirth());
        }

        if (Objects.nonNull(userInfo.getImage())) {
            String profileName = objectStorageService.uploadFile(userInfo.getImage());
            memberMapper.updateUserProfileByEmail(userEmail, profileName);
        }

        return ResponseEntity.ok("");
    }

    @Override
    @Transactional
    public void deleteMemberByEmail(String[] email) {
        Arrays.stream(email).forEach(memberMapper::deleteMemberByEmail);
    }

    @Override
    public boolean isDuplicateName(String name) {
        return memberMapper.isDuplicateName(name);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<?> getMemberListByFilter(FilterType filterType,
                                         String searchString,
                                         int page,
                                         Date startDate, Date endDate,
                                         boolean isAuthor, boolean isActiveMember) {
        int[] pageIndex = Page.getPageIndex(page);

        switch (filterType) {
            case all -> {
                return handleAllFilter(startDate, endDate, pageIndex, isAuthor, isActiveMember);
            }

            case email -> {
                return handleEmailFilter(searchString, startDate, endDate, pageIndex, isAuthor, isActiveMember);
            }

            case name -> {
                return handleNameFilter(searchString, startDate, endDate, pageIndex, isAuthor, isActiveMember);
            }

            default -> {
                log.error("[{}] Parsing Error.. Wrong Filter Type.", getClass().getSimpleName());
                throw new RuntimeException();
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public List<?> handleAllFilter(Date startDate, Date endDate, int[] pageIndex,
                                   boolean isAuthor, boolean isActiveMember) {
        if (Objects.nonNull(startDate)) {
            return isAuthor ? getArtistMembersByDate(startDate, endDate, pageIndex, isActiveMember) :
                    getMembersByDate(startDate, endDate, pageIndex, isActiveMember);
        } else {
            return isAuthor ? getArtistMembers(pageIndex, isActiveMember) :
                    entityToResponse(memberMapper.getAllMembers(pageIndex[0], pageIndex[1], isActiveMember));
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public List<?> handleEmailFilter(String searchString, Date startDate, Date endDate,
                                     int[] pageIndex, boolean isAuthor, boolean isActiveMember) {
        if (Objects.nonNull(startDate)) {
            return isAuthor ? getArtistMembersByDateAndEmail(searchString, startDate, endDate, pageIndex,
                    isActiveMember) :
                    getMembersByDateAndEmail(searchString, startDate, endDate, pageIndex, isActiveMember);
        } else {
            return isAuthor ? getArtistMembersByEmail(searchString, pageIndex, isActiveMember) :
                    entityToResponse(
                            memberMapper.getMembersByEmail(searchString, pageIndex[0], pageIndex[1], isActiveMember));
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public List<?> handleNameFilter(String searchString, Date startDate, Date endDate,
                                    int[] pageIndex, boolean isAuthor, boolean isActiveMember) {
        if (Objects.nonNull(startDate)) {
            return isAuthor ? getArtistMembersByDateAndName(searchString, startDate, endDate, pageIndex, isActiveMember)
                    :
                            getMembersByDateAndName(searchString, startDate, endDate, pageIndex, isActiveMember);
        } else {
            return isAuthor ? getArtistMembersByName(searchString, pageIndex, isActiveMember) :
                    entityToResponse(
                            memberMapper.getMembersByName(searchString, pageIndex[0], pageIndex[1], isActiveMember));
        }
    }

    private List<Members> getArtistMembers(int[] pageIndex, boolean isActiveMember) {
        return entityToResponse(memberMapper.getArtisMember(pageIndex[0], pageIndex[1], isActiveMember));
    }

    private List<Members> getArtistMembersByEmail(String searchString, int[] pageIndex, boolean isActiveMember) {
        return entityToResponse(
                memberMapper.getArtistMembersByEmail(searchString, pageIndex[0], pageIndex[1], isActiveMember));
    }

    private List<Members> getArtistMembersByName(String searchString, int[] pageIndex, boolean isActiveMember) {
        return entityToResponse(
                memberMapper.getArtistMembersByName(searchString, pageIndex[0], pageIndex[1], isActiveMember));
    }


    private List<Members> getMembersByDate(Date startDate, Date endDate, int[] pageIndex, boolean isActiveMember) {
        return entityToResponse(
                memberMapper.getMembersByDate(startDate, endDate, pageIndex[0], pageIndex[1], isActiveMember));
    }


    private List<Members> getMembersByDateAndEmail(String searchString, Date startDate, Date endDate, int[] pageIndex,
                                                   boolean isActiveMember) {
        return entityToResponse(
                memberMapper.getMembersByDateAndEmail(searchString, startDate, endDate, pageIndex[0], pageIndex[1],
                        isActiveMember));
    }

    private List<Members> getMembersByDateAndName(String searchString, Date startDate, Date endDate, int[] pageIndex,
                                                  boolean isActiveMember) {
        return entityToResponse(
                memberMapper.getMembersByDateAndName(searchString, startDate, endDate, pageIndex[0], pageIndex[1],
                        isActiveMember));
    }


    private List<Members> getArtistMembersByDate(Date startDate, Date endDate, int[] pageIndex,
                                                 boolean isActiveMember) {
        return entityToResponse(
                memberMapper.getArtistMembersByDate(startDate, endDate, pageIndex[0], pageIndex[1], isActiveMember));
    }


    private List<Members> getArtistMembersByDateAndEmail(String searchString, Date startDate, Date endDate,
                                                         int[] pageIndex, boolean isActiveMember) {
        return entityToResponse(
                memberMapper.getArtistMembersByDateAndEmail(searchString, startDate, endDate, pageIndex[0],
                        pageIndex[1], isActiveMember));
    }

    private List<Members> getArtistMembersByDateAndName(String searchString, Date startDate, Date endDate,
                                                        int[] pageIndex, boolean isActiveMember) {
        return entityToResponse(
                memberMapper.getArtistMembersByDateAndName(searchString, startDate, endDate, pageIndex[0], pageIndex[1],
                        isActiveMember));
    }

    //TODO : 비동기 동작하도록 구현 필요
    private List<Members> entityToResponse(List<Member> members) {
        List<Members> memberList = new ArrayList<>(members.size());
        members.forEach(member -> memberList.add(
                Members.builder()
                        .num(1)
                        .email(member.getEmail())
                        .name(member.getName())
                        .isAuthor(member.isArtist())
                        .createAt(member.getCreateAt())
                        .updateAt(member.getNicknameUpdateAt()).build()));
        return memberList;
    }
}
