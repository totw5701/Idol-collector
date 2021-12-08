package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.service.MemberService;
import com.idolcollector.idolcollector.service.PostService;
import com.idolcollector.idolcollector.web.dto.post.HomePostListResponseDto;
import com.idolcollector.idolcollector.web.dto.post.PostResponseDto;
import com.idolcollector.idolcollector.web.dto.post.PostSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.post.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
public class ApiCardController {

    private final PostService postService;
    private final MemberService memberService;

    @GetMapping("/home-cards")
    public List<HomePostListResponseDto> homeList() {
        return postService.scorePostList();
    }

    @GetMapping("/{id}")
    public PostResponseDto detail(@PathVariable("id") Long id) {
        return postService.detail(id);
    }


}
