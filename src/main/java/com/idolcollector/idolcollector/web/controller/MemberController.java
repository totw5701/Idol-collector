package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.domain.blame.Blame;
import com.idolcollector.idolcollector.domain.notice.NoticeRepository;
import com.idolcollector.idolcollector.service.BundleService;
import com.idolcollector.idolcollector.service.MemberService;
import com.idolcollector.idolcollector.service.PostService;
import com.idolcollector.idolcollector.service.ResponseService;
import com.idolcollector.idolcollector.web.dto.blame.BlameRequestDto;
import com.idolcollector.idolcollector.web.dto.bundle.BundleResponseDto;
import com.idolcollector.idolcollector.web.dto.member.MemberBrifInfo;
import com.idolcollector.idolcollector.web.dto.member.MemberDetailDto;
import com.idolcollector.idolcollector.web.dto.notice.NoticeResponseDto;
import com.idolcollector.idolcollector.web.dto.pageresponsedto.MemberDetailPageDto;
import com.idolcollector.idolcollector.web.dto.pageresponsedto.MyDetailPageDto;
import com.idolcollector.idolcollector.web.dto.post.HomePostListResponseDto;
import com.idolcollector.idolcollector.web.dto.response.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;
    private final PostService postService;
    private final BundleService bundleService;

    private final HttpSession httpSession;

    private final ResponseService responseService;

    @GetMapping("/notice")
    public CommonResult noticeConfirm() {
        List<NoticeResponseDto> noticeResponseDtos = memberService.noticeConfirm();
        return responseService.getResult(noticeResponseDtos);
    }

    @PostMapping("/blame")
    public CommonResult blame(@Validated @RequestBody BlameRequestDto form) {
        memberService.blame(form);
        return responseService.getSuccessResult();
    }

    @GetMapping({"/member/{id}/{page}", "/member/{id}"})
    public CommonResult memberInfo(@PathVariable(name = "page", required = false) Optional<Integer> page,
                                   @PathVariable(name = "id") Long memberId,
                                   HttpServletResponse res,
                                   HttpServletRequest req) throws ServletException, IOException {

        Long sessionId = (Long) httpSession.getAttribute("loginMember");
        if(memberId == sessionId) req.getRequestDispatcher("/api/mypage").forward(req, res);


        int pageNum = 0;
        if (page.isPresent()) pageNum = page.get();

        // 세션에서 멤버정보 받아오기
        MemberBrifInfo member = new MemberBrifInfo(memberService.findById(memberId));

        List<HomePostListResponseDto> cards = postService.memberPostList(memberId, pageNum);

        List<BundleResponseDto> bundles = bundleService.findAllInMember(member.getId());

        return responseService.getResult(new MemberDetailPageDto(member, bundles, cards));
    }

    @GetMapping({"/mypage/{page}", "/mypage"})
    public CommonResult myInfo(@PathVariable(name = "page", required = false) Optional<Integer> page) {

        int pageNum = 0;
        if (page.isPresent()) pageNum = page.get();


        Long memberId = (Long) httpSession.getAttribute("loginMember");

        // 세션에서 멤버정보 받아오기
        MemberDetailDto member = memberService.findById(memberId);

        List<HomePostListResponseDto> cards = postService.memberPostList(memberId, pageNum);

        List<BundleResponseDto> bundles = bundleService.findAllInMember(member.getId());

        return responseService.getResult(new MyDetailPageDto(member, bundles, cards));
    }



}
