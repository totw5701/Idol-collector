package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.domain.notice.NoticeRepository;
import com.idolcollector.idolcollector.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/notice")
    public void noticeConfirm() {
        memberService.noticeConfirm();
    }

}
