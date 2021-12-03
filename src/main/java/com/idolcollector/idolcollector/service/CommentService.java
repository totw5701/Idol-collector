package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.comment.CommentRepository;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedCommentRepository;
import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.post.PostRepository;
import com.idolcollector.idolcollector.web.dto.comment.CommentResponseDto;
import com.idolcollector.idolcollector.web.dto.comment.CommentSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.comment.CommentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final NestedCommentRepository nestedCommentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public CommentResponseDto findById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다. id=" + id));

        return new CommentResponseDto(comment);
    }

    @Transactional
    public Long save(CommentSaveRequestDto form) {
        Member member = memberRepository.findById(form.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. id=" + form.getAuthorId()));
        Post post = postRepository.findById(form.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. id=" + form.getPostId()));

        Comment save = commentRepository.save(new Comment(member, post, form.getContent()));
        return save.getId();
    }

    @Transactional
    public Long update(CommentUpdateRequestDto form) {
        Comment comment = commentRepository.findById(form.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다. id=" + form.getId()));

        return comment.update(form.getContent());
    }


    @Transactional
    public Long delete(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다. id=" + id));

        commentRepository.delete(comment);
        return comment.getId();
    }


    @Transactional
    public int like(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다. id=" + id));

        return comment.addLike();
    }

    public List<CommentResponseDto> findAllInPost(Long postId) {

        List<Comment> comments = commentRepository.findAllByPostId(postId);

        List<CommentResponseDto> list = new ArrayList<>();

        for (Comment comment : comments) {
            list.add(new CommentResponseDto(comment));
        }

        return list;
    }

    public List<CommentResponseDto> findAllInMember(Long memberId) {

        List<Comment> comments = commentRepository.findAllByMemberId(memberId);

        List<CommentResponseDto> list = new ArrayList<>();

        for (Comment comment : comments) {
            list.add(new CommentResponseDto(comment));
        }

        return list;
    }
}
