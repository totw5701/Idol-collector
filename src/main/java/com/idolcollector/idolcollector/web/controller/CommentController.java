package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.service.CommentService;
import com.idolcollector.idolcollector.service.NestedCommentService;
import com.idolcollector.idolcollector.service.ResponseService;
import com.idolcollector.idolcollector.web.dto.comment.CommentSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.comment.CommentUpdateRequestDto;
import com.idolcollector.idolcollector.web.dto.post.PostSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.response.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final ResponseService responseService;

    @PostMapping(value = "/create")
    public CommonResult create(@Validated @RequestBody CommentSaveRequestDto form) throws IOException {
        commentService.save(form);
        return responseService.getSuccessResult();
    }

    @PutMapping("/update")
    public CommonResult update(@Validated @RequestBody CommentUpdateRequestDto form) {
        commentService.update(form);
        return responseService.getSuccessResult();
    }

    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id) {
        commentService.delete(id);
        return responseService.getSuccessResult();
    }

    @PutMapping("/like/{id}")
    public CommonResult addLike(@PathVariable("id") Long id) {
        commentService.like(id);
        return responseService.getSuccessResult();
    }
}
