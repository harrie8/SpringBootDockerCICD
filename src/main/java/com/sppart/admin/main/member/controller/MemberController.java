package com.sppart.admin.main.member.controller;

import com.sppart.admin.main.member.dto.UpdateUserInfo;
import com.sppart.admin.main.member.service.MemberService;
import com.sppart.admin.utils.FilterType;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<?> getAllMembers(@RequestParam(value = "startDate", required = false) Date startDate,
                                           @RequestParam(value = "endDate", required = false) Date endDate,
                                           @RequestParam(value = "isAuthor", defaultValue = "false") boolean isAuthor,
                                           @RequestParam(value = "searchString", required = false) String searchString,
                                           @RequestParam(value = "filterType", defaultValue = "all") FilterType filterType,
                                           @RequestParam(value = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok(
                memberService.getAllMembers(startDate, endDate, isAuthor, searchString, filterType, page));
    }

    @GetMapping("/inactive")
    public ResponseEntity<?> getAllDeleteMembers(@RequestParam(value = "startDate", required = false) Date startDate,
                                                 @RequestParam(value = "endDate", required = false) Date endDate,
                                                 @RequestParam(value = "isAuthor", defaultValue = "false") boolean isAuthor,
                                                 @RequestParam(value = "searchString", required = false) String searchString,
                                                 @RequestParam(value = "filterType", defaultValue = "all") FilterType filterType,
                                                 @RequestParam(value = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok(
                memberService.getDeleteMembers(startDate, endDate, isAuthor, searchString, filterType, page));
    }

    @GetMapping("/one")
    public ResponseEntity<?> getMemberByEmail(@RequestParam(value = "email") String email) {
        return ResponseEntity.ok(memberService.getMemberByEmail(email));
    }

    @PutMapping("/one")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> editMemberInfo(@RequestBody UpdateUserInfo userInfo) {
        return memberService.editMemberInfo(userInfo);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteMemberByEmail(@RequestParam(value = "email") String[] email) {
        memberService.deleteMemberByEmail(email);
    }

    @GetMapping("/check-name")
    public ResponseEntity<?> checkDuplicateName(@RequestParam("name") String name) {
        if (memberService.isDuplicateName(name)) {
            return ResponseEntity.ok("success.");
        } else {
            return ResponseEntity.badRequest().body("failed.");
        }
    }
}
