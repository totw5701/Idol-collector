package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.comment.CommentRepository;
import com.idolcollector.idolcollector.domain.post.PostRepository;
import com.idolcollector.idolcollector.web.dto.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    public List<CommentResponseDto> findAllInPost(Long postId) {

        List<Comment> comments = commentRepository.findAllInPost(postId);

        // 대댓글 넣기

        List<CommentResponseDto> list = new ArrayList<>();

        for (Comment comment : comments) {
            list.add(new CommentResponseDto(comment));
        }

        return list;
    }
}
