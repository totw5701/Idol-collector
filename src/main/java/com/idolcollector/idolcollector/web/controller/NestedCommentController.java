package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.service.NestedCommentService;
import com.idolcollector.idolcollector.service.ResponseService;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentUpdateRequestDto;
import com.idolcollector.idolcollector.web.dto.response.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/n-comment")
@RequiredArgsConstructor
public class NestedCommentController {

    private final NestedCommentService nestedCommentService;

    private final ResponseService responseService;

    @PostMapping(value = "/create")
    public CommonResult create(@Validated @RequestBody NestedCommentSaveRequestDto form){
        nestedCommentService.save(form);
        return responseService.getSuccessResult();
    }

    @PutMapping("/update")
    public CommonResult update(@Validated @RequestBody NestedCommentUpdateRequestDto form) {
        nestedCommentService.update(form);
        return responseService.getSuccessResult();

    }

    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id) {

        nestedCommentService.delete(id);
        return responseService.getSuccessResult();

    }

    @PutMapping("/like/{id}")
    public CommonResult addLike(@PathVariable("id") Long id) {
        nestedCommentService.like(id);
        return responseService.getSuccessResult();

    }
}
