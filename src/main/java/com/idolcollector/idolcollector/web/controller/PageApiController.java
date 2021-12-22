package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.service.BundleService;
import com.idolcollector.idolcollector.service.MemberService;
import com.idolcollector.idolcollector.service.PostService;
import com.idolcollector.idolcollector.web.dto.bundle.BundleResponseDto;
import com.idolcollector.idolcollector.web.dto.member.MemberResponseDto;
import com.idolcollector.idolcollector.web.dto.pageresponsedto.CardDetailPageDto;
import com.idolcollector.idolcollector.web.dto.pageresponsedto.MemberDetailPageDto;
import com.idolcollector.idolcollector.web.dto.pageresponsedto.RootPageDto;
import com.idolcollector.idolcollector.web.dto.post.HomePostListResponseDto;
import com.idolcollector.idolcollector.web.dto.post.PostResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PageApiController {

    private final PostService postService;
    private final MemberService memberService;
    private final BundleService bundleService;

    private final HttpSession httpSession;


    @GetMapping({"/home/{page}", "/home"})
    public RootPageDto homePageList(@PathVariable(name = "page", required = false) Optional<Integer> page) {

        int pageNum = 0;
        if (page.isPresent()) pageNum = page.get();

        List<HomePostListResponseDto> homePostListResponseDtos = postService.scorePostList(pageNum);

        // 세션에서 멤버정보 받아오기
        MemberResponseDto memberResponseDto = memberService.findById((Long) httpSession.getAttribute("loginMember"));

        String s = memberResponseDto.getNotices().toString();
        System.out.println("noticeREsponse dto = " + s);
        return new RootPageDto(homePostListResponseDtos, memberResponseDto);
    }

    @GetMapping("/card/{id}")
    public CardDetailPageDto detail(@PathVariable("id") Long id) {

        PostResponseDto post = postService.detail(id);

        // 세션에서 멤버정보 받아오기
        MemberResponseDto member = memberService.findById((Long) httpSession.getAttribute("loginMember"));

        return new CardDetailPageDto(post, member);
    }

    @GetMapping("/member/{id}")
    public MemberDetailPageDto myInfo() {
        // 세션에서 멤버정보 받아오기
        MemberResponseDto member = memberService.findById((Long) httpSession.getAttribute("loginMember"));


        List<BundleResponseDto> bundles = bundleService.findAllInMember(member.getId());

        return new MemberDetailPageDto(member, bundles);
    }

}
