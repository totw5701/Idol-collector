package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.comment.CommentRepository;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedComment;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedCommentRepository;
import com.idolcollector.idolcollector.domain.post.PostRepository;
import com.idolcollector.idolcollector.web.dto.CommentResponseDto;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentResponseDto;
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
    private final NestedCommentRepository nestedCommentRepository;


    public List<CommentResponseDto> findAllInPost(Long postId) {

        List<Comment> comments = commentRepository.findAllInPost(postId);

        List<CommentResponseDto> list = new ArrayList<>();

        for (Comment comment : comments) {
            List<NestedComment> nComments = nestedCommentRepository.findAllInComment(comment.getId());
            list.add(new CommentResponseDto(comment, nComments));
        }

        return list;
    }
}
