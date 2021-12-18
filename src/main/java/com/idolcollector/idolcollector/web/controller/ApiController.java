package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.service.MemberService;
import com.idolcollector.idolcollector.service.PostService;
import com.idolcollector.idolcollector.web.dto.post.HomePostListResponseDto;
import com.idolcollector.idolcollector.web.dto.post.PostResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final PostService postService;
    private final MemberService memberService;

    @GetMapping("/home")
    public List<HomePostListResponseDto> homeList() {

        List<HomePostListResponseDto> homePostListResponseDtos = postService.scorePostList();

        return null;
    }

    @GetMapping("/home/{page}")
    public List<HomePostListResponseDto> homePageList() {


        return null;
    }

    @GetMapping("/card/{id}")
    public PostResponseDto detail(@PathVariable("id") Long id) {
        return postService.detail(id);
    }

}
