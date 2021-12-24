package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.domain.blame.Blame;
import com.idolcollector.idolcollector.domain.notice.NoticeRepository;
import com.idolcollector.idolcollector.service.MemberService;
import com.idolcollector.idolcollector.web.dto.blame.BlameRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/blame")
    public void blame(@Validated @RequestBody BlameRequestDto form) {
        memberService.blame(form);
    }

}
