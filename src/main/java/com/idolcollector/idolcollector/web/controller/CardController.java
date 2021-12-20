package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.file.FileStore;
import com.idolcollector.idolcollector.service.PostService;
import com.idolcollector.idolcollector.web.dto.file.UploadFile;
import com.idolcollector.idolcollector.web.dto.post.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/card")
public class CardController {

    private final PostService postService;
    private final FileStore fileStore;

    @PostMapping("/create")
    public String create(@ModelAttribute PostSaveRequestDto form) throws IOException {

        Long cardId = postService.create(form);
        return "redirect:/card/" + cardId;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        postService.delete(id);
    }

    @PostMapping("/like/{id}")
    public void addLike(@PathVariable("id") Long id) {
        postService.like(id);
    }

    @GetMapping("/scrap/{id}")
    public void scrap(@PathVariable("id") Long id) {
        postService.scrap(id);
    }


    @ResponseBody
    @GetMapping("/images/{fileName}")
    public Resource imageFile(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(fileName));
    }
}
