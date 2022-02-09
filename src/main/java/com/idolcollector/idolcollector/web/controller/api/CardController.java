package com.idolcollector.idolcollector.web.controller.api;

import com.idolcollector.idolcollector.file.FileStore;
import com.idolcollector.idolcollector.service.MemberService;
import com.idolcollector.idolcollector.service.PostService;
import com.idolcollector.idolcollector.service.ResponseService;
import com.idolcollector.idolcollector.web.dto.comment.CommentUpdateRequestDto;
import com.idolcollector.idolcollector.web.dto.file.UploadFile;
import com.idolcollector.idolcollector.web.dto.member.MemberDetailDto;
import com.idolcollector.idolcollector.web.dto.pageresponsedto.CardDetailPageDto;
import com.idolcollector.idolcollector.web.dto.post.PostResponseDto;
import com.idolcollector.idolcollector.web.dto.post.PostSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.post.PostUpdateRequestDto;
import com.idolcollector.idolcollector.web.dto.response.CommonResult;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

@Api(tags = {"카드"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card")
public class CardController {

    private final PostService postService;
    private final FileStore fileStore;
    private final MemberService memberService;
    private final HttpSession httpSession;

    private final ResponseService responseService;

    @ApiOperation(value = "카드 생성", notes = "카드를 생성합니다.")
    @PostMapping()
    public CommonResult<Long> create(@ApiParam @Validated @ModelAttribute PostSaveRequestDto form) throws IOException {

        Long id = postService.create(form);

        return responseService.getResult(id);
    }

    @ApiOperation(value = "카드 상세정보", notes = "카드 상세정보를 조회합니다.")
    @GetMapping("/{id}")
    public CommonResult<CardDetailPageDto> detail(@PathVariable("id") Long id) {

        PostResponseDto post = postService.detail(id);

        // 세션에서 멤버정보 받아오기
        MemberDetailDto member = memberService.findById((Long) httpSession.getAttribute("loginMember"));

        return responseService.getResult(new CardDetailPageDto(post, member));
    }

    @ApiOperation(value = "카드 수정", notes = "카드를 수정합니다.")
    @PatchMapping()
    public CommonResult<Object> update(@ApiParam @Validated @RequestBody PostUpdateRequestDto form) {

        postService.update(form);

        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "카드 삭제", notes = "카드를 삭제합니다.")
    @DeleteMapping("/{id}")
    public CommonResult<Object> delete(@PathVariable("id") Long id) {

        postService.delete(id);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "카드 좋아요", notes = "이 카드를 좋아합니다.")
    @PatchMapping("/like/{id}")
    public CommonResult<Object> addLike(@PathVariable("id") Long id) {

        postService.like(id);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "카드 스크랩", notes = "이 카드를 스크랩합니다.")
    @PutMapping("/scrap/{id}")
    public CommonResult<Long> scrap(@PathVariable("id") Long id) {

        Long scrapId = postService.scrap(id);
        return responseService.getResult(scrapId);
    }

    @ApiOperation(value = "스크랩 취소", notes = "스크랩을 취소합니다.")
    @DeleteMapping("/unscrap/{id}")
    public CommonResult<Object> unscrap(@PathVariable("id") Long id) {
        postService.cancelScrap(id);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "이미지 파일 받아오기", notes = "이미지 파일을 받아옵니다.")
    @GetMapping("/image/{fileName}")
    public Resource imageFile(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(fileName));
    }
}
