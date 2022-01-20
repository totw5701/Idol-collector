package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.file.FileStore;
import com.idolcollector.idolcollector.service.PostService;
import com.idolcollector.idolcollector.service.ResponseService;
import com.idolcollector.idolcollector.web.dto.comment.CommentUpdateRequestDto;
import com.idolcollector.idolcollector.web.dto.file.UploadFile;
import com.idolcollector.idolcollector.web.dto.post.PostSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.post.PostUpdateRequestDto;
import com.idolcollector.idolcollector.web.dto.response.CommonResult;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card")
public class CardController {

    private final PostService postService;
    private final FileStore fileStore;

    private final ResponseService responseService;

    @PostMapping(value = "/create")
    public CommonResult create(@Validated @ModelAttribute PostSaveRequestDto form) throws IOException {

        Long id = postService.create(form);

        return responseService.getResult(id);
    }

    @PutMapping("/update")
    public CommonResult update(@Validated @RequestBody PostUpdateRequestDto form) {

        postService.update(form);

        return responseService.getSuccessResult();
    }

    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id) {

        postService.delete(id);
        return responseService.getSuccessResult();
    }

    @PutMapping("/like/{id}")
    public CommonResult addLike(@PathVariable("id") Long id) {

        postService.like(id);
        return responseService.getSuccessResult();
    }

    @PutMapping("/scrap/{id}")
    public CommonResult scrap(@PathVariable("id") Long id) {

        Long scrapId = postService.scrap(id);
        return responseService.getResult(scrapId);
    }

    @DeleteMapping("/unscrap/{id}")
    public CommonResult unscrap(@PathVariable("id") Long id) {
        postService.cancelScrap(id);
        return responseService.getSuccessResult();
    }

    @GetMapping("/image/{fileName}")
    public Resource imageFile(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(fileName));
    }
}
