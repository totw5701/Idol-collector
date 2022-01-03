package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.service.NestedCommentService;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentUpdateRequestDto;
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

    @PostMapping(value = "/create")
    public Long create(@Validated @RequestBody NestedCommentSaveRequestDto form){
        return nestedCommentService.save(form);
    }

    @PutMapping("/update")
    public Long update(@Validated @RequestBody NestedCommentUpdateRequestDto form) {
        return nestedCommentService.update(form);
    }

    @DeleteMapping("/delete/{id}")
    public Long delete(@PathVariable("id") Long id) {
        return nestedCommentService.delete(id);
    }

    @PutMapping("/like/{id}")
    public int addLike(@PathVariable("id") Long id) {
        return nestedCommentService.like(id);
    }
}
