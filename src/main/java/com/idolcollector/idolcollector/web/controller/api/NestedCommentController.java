package com.idolcollector.idolcollector.web.controller.api;

import com.idolcollector.idolcollector.service.NestedCommentService;
import com.idolcollector.idolcollector.service.ResponseService;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentResponseDto;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentUpdateRequestDto;
import com.idolcollector.idolcollector.web.dto.response.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"대댓글"})
@Slf4j
@RestController
@RequestMapping("/api/n-comments")
@RequiredArgsConstructor
public class NestedCommentController {

    private final NestedCommentService nestedCommentService;

    private final ResponseService responseService;

    @ApiOperation(value = "대댓글 생성", notes = "대댓글 생성한다.")
    @PostMapping()
    public CommonResult<NestedCommentResponseDto> create(@ApiParam @Validated @RequestBody NestedCommentSaveRequestDto form){
        NestedCommentResponseDto save = nestedCommentService.save(form);
        return responseService.getResult(save);
    }

    @ApiOperation(value = "대댓글 수정", notes = "대댓글 수정한다.")
    @PatchMapping()
    public CommonResult<Object> update(@ApiParam @Validated @RequestBody NestedCommentUpdateRequestDto form) {
        nestedCommentService.update(form);
        return responseService.getSuccessResult();

    }

    @ApiOperation(value = "대댓글 삭제", notes = "대댓글 삭제한다.")
    @DeleteMapping("/{id}")
    public CommonResult<Object> delete(@PathVariable("id") Long id) {

        nestedCommentService.delete(id);
        return responseService.getSuccessResult();

    }

    @ApiOperation(value = "대댓글 좋아요", notes = "대댓글 좋아요한다.")
    @PatchMapping("/like/{id}")
    public CommonResult<Object> addLike(@PathVariable("id") Long id) {
        nestedCommentService.like(id);
        return responseService.getSuccessResult();

    }
}
