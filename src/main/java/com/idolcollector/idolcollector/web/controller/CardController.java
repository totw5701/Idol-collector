package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.file.FileStore;
import com.idolcollector.idolcollector.service.PostService;
import com.idolcollector.idolcollector.web.dto.file.UploadFile;
import com.idolcollector.idolcollector.web.dto.post.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CardController {

    private final PostService postService;

    private final FileStore fileStore;


    @PostMapping("/card/create")
    public String create(@ModelAttribute PostSaveRequestDto form) throws IOException {

        Long cardId = postService.create(form);

        return "redirect:/card/" + cardId;
    }


}
