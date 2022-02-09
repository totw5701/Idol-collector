package com.idolcollector.idolcollector.web.controller.api;

import com.idolcollector.idolcollector.service.CommentService;
import com.idolcollector.idolcollector.service.NestedCommentService;
import com.idolcollector.idolcollector.service.ResponseService;
import com.idolcollector.idolcollector.web.dto.comment.CommentSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.comment.CommentUpdateRequestDto;
import com.idolcollector.idolcollector.web.dto.post.PostSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.response.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Api(tags = {"댓글"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final ResponseService responseService;

    @ApiOperation(value = "댓글 생성", notes = "댓글을 생성합니다.")
    @PostMapping()
    public CommonResult<Object> create(@ApiParam @Validated @RequestBody CommentSaveRequestDto form) throws IOException {
        commentService.save(form);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정합니다.")
    @PatchMapping()
    public CommonResult<Object> update(@ApiParam @Validated @RequestBody CommentUpdateRequestDto form) {
        commentService.update(form);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제합니다.")
    @DeleteMapping("/{id}")
    public CommonResult<Object> delete(@PathVariable("id") Long id) {
        commentService.delete(id);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "좋아요", notes = "이 댓글을 좋아합니다.")
    @PatchMapping("/like/{id}")
    public CommonResult<Object> addLike(@PathVariable("id") Long id) {
        commentService.like(id);
        return responseService.getSuccessResult();
    }
}
