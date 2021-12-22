package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.service.CommentService;
import com.idolcollector.idolcollector.service.NestedCommentService;
import com.idolcollector.idolcollector.web.dto.comment.CommentSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.post.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final NestedCommentService nestedCommentService;

    @PostMapping(value = "/create")
    public String create(@RequestBody CommentSaveRequestDto form) throws IOException {

        Long id = commentService.save(form);
        return "redirect:/card/" + form.getPostId();
    }


}
