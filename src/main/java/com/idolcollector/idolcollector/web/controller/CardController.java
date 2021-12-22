package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.file.FileStore;
import com.idolcollector.idolcollector.service.PostService;
import com.idolcollector.idolcollector.web.dto.file.UploadFile;
import com.idolcollector.idolcollector.web.dto.post.PostSaveRequestDto;
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
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/card")
public class CardController {

    private final PostService postService;
    private final FileStore fileStore;

    @PostMapping(value = "/create")
    public String create(@ModelAttribute PostSaveRequestDto form) throws IOException {

        Long cardId = postService.create(form);
        return "redirect:/card/" + cardId;
    }

    @ResponseBody
    @DeleteMapping("/delete/{id}")
    public Long delete(@PathVariable("id") Long id) {
        return postService.delete(id);
    }

    @ResponseBody
    @PutMapping("/like/{id}")
    public int addLike(@PathVariable("id") Long id) {
        return postService.like(id);
    }

    @PutMapping("/scrap/{id}")
    public void scrap(@PathVariable("id") Long id) {
        postService.scrap(id);
    }


    @ResponseBody
    @GetMapping("/image/{fileName}")
    public Resource imageFile(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(fileName));
    }
}
