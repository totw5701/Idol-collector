package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.file.FileStore;
import com.idolcollector.idolcollector.service.PostService;
import com.idolcollector.idolcollector.web.dto.comment.CommentUpdateRequestDto;
import com.idolcollector.idolcollector.web.dto.file.UploadFile;
import com.idolcollector.idolcollector.web.dto.post.PostSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.post.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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

    @PostMapping(value = "/create")
    public Long create(@ModelAttribute PostSaveRequestDto form) throws IOException {

        return postService.create(form);
    }

    @PutMapping("/update")
    public Long update(@RequestBody PostUpdateRequestDto form) {

        return postService.update(form);
    }

    @DeleteMapping("/delete/{id}")
    public Long delete(@PathVariable("id") Long id) {
        return postService.delete(id);
    }

    @PutMapping("/like/{id}")
    public int addLike(@PathVariable("id") Long id) {
        return postService.like(id);
    }

    @PutMapping("/scrap/{id}")
    public Long scrap(@PathVariable("id") Long id) {
        return postService.scrap(id);
    }


    @GetMapping("/image/{fileName}")
    public Resource imageFile(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(fileName));
    }
}
