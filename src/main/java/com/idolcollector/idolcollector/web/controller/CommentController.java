package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.service.CommentService;
import com.idolcollector.idolcollector.service.NestedCommentService;
import com.idolcollector.idolcollector.web.dto.comment.CommentSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.comment.CommentUpdateRequestDto;
import com.idolcollector.idolcollector.web.dto.post.PostSaveRequestDto;
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
    private final NestedCommentService nestedCommentService;

    @PostMapping(value = "/create")
    public Long create(@Validated @RequestBody CommentSaveRequestDto form) throws IOException {
        return commentService.save(form);
    }

    @PutMapping("/update")
    public Long update(@Validated @RequestBody CommentUpdateRequestDto form) {
        return commentService.update(form);
    }

    @DeleteMapping("/delete/{id}")
    public Long delete(@PathVariable("id") Long id) {
        return commentService.delete(id);
    }

    @PutMapping("/like/{id}")
    public int addLike(@PathVariable("id") Long id) {
        return commentService.like(id);
    }
}
