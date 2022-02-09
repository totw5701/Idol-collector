package com.idolcollector.idolcollector.web.controller.api;

import com.idolcollector.idolcollector.domain.blame.Blame;
import com.idolcollector.idolcollector.domain.notice.NoticeRepository;
import com.idolcollector.idolcollector.file.FileStore;
import com.idolcollector.idolcollector.service.BundleService;
import com.idolcollector.idolcollector.service.MemberService;
import com.idolcollector.idolcollector.service.PostService;
import com.idolcollector.idolcollector.service.ResponseService;
import com.idolcollector.idolcollector.web.dto.blame.BlameRequestDto;
import com.idolcollector.idolcollector.web.dto.bundle.BundleResponseDto;
import com.idolcollector.idolcollector.web.dto.member.MemberBrifInfo;
import com.idolcollector.idolcollector.web.dto.member.MemberDetailDto;
import com.idolcollector.idolcollector.web.dto.member.MemberUpdateRequestDto;
import com.idolcollector.idolcollector.web.dto.notice.NoticeResponseDto;
import com.idolcollector.idolcollector.web.dto.pageresponsedto.MemberDetailPageDto;
import com.idolcollector.idolcollector.web.dto.pageresponsedto.MyDetailPageDto;
import com.idolcollector.idolcollector.web.dto.post.HomePostListResponseDto;
import com.idolcollector.idolcollector.web.dto.response.CommonResult;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@Api(tags = {"회원"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;
    private final PostService postService;
    private final BundleService bundleService;
    private final FileStore fileStore;

    private final HttpSession httpSession;

    private final ResponseService responseService;

    @ApiOperation(value = "알림 확인", notes = "알림은 확인과 동시에 삭제됩니다.")
    @GetMapping("/notice")
    public CommonResult<List<NoticeResponseDto>> noticeConfirm() {
        List<NoticeResponseDto> noticeResponseDtos = memberService.noticeConfirm();
        return responseService.getResult(noticeResponseDtos);
    }

    @ApiOperation(value = "신고", notes = "다른 회원을 신고합니다.")
    @PostMapping("/blame")
    public CommonResult<Object> blame(@Validated @RequestBody BlameRequestDto form) {
        memberService.blame(form);
        return responseService.getSuccessResult();
    }


    @ApiOperation(value = "회원 정보", notes = "타 회원 정보를 조회합니다. 회원 id를 넘겨 회원 정보와 카드, 카드집을 조회합니다. page를 넣어 다음 페이지를 조회합니다.")
    @GetMapping({"/member/{id}/{page}", "/member/{id}"})
    public CommonResult<MemberDetailPageDto> memberInfo(@PathVariable(name = "page", required = false) Optional<Integer> page,
                                   @PathVariable(name = "id") Long memberId,
                                   HttpServletResponse res,
                                   HttpServletRequest req) throws ServletException, IOException {

        Long sessionId = (Long) httpSession.getAttribute("loginMember");
        if (memberId == sessionId) req.getRequestDispatcher("/api/mypage").forward(req, res);


        int pageNum = 0;
        if (page.isPresent()) pageNum = page.get();

        // 세션에서 멤버정보 받아오기
        MemberBrifInfo member = new MemberBrifInfo(memberService.findById(memberId));

        List<HomePostListResponseDto> cards = postService.memberPostList(memberId, pageNum);

        List<BundleResponseDto> bundles = bundleService.findAllInMember(member.getId());

        return responseService.getResult(new MemberDetailPageDto(member, bundles, cards));
    }


    @ApiOperation(value = "내 정보", notes = "회원 정보를 조회합니다. 회원 id를 넘겨 회원 정보와 카드, 카드집을 조회합니다. page를 넣어 다음 페이지를 조회합니다.")
    @GetMapping({"/mypage/{page}", "/mypage"})
    public CommonResult<MyDetailPageDto> myInfo(@PathVariable(name = "page", required = false) Optional<Integer> page) {

        int pageNum = 0;
        if (page.isPresent()) pageNum = page.get();


        Long memberId = (Long) httpSession.getAttribute("loginMember");

        // 세션에서 멤버정보 받아오기
        MemberDetailDto member = memberService.findById(memberId);

        List<HomePostListResponseDto> cards = postService.memberPostList(memberId, pageNum);

        List<BundleResponseDto> bundles = bundleService.findAllInMember(member.getId());

        return responseService.getResult(new MyDetailPageDto(member, bundles, cards));
    }

    @ApiOperation(value = "회원 정보수정", notes = "회원 정보를 수정합니다.")
    @PatchMapping("/mypage")
    public CommonResult<Object> updateMember(@ApiParam @Validated @ModelAttribute MemberUpdateRequestDto form) throws IOException {

        memberService.update(form);

        return responseService.getSuccessResult();
    }

    @GetMapping("/image/{fileName}")
    public Resource imageFile(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getProfileFullPath(fileName));
    }
}
