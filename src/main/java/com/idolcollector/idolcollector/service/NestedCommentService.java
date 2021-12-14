package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.comment.CommentRepository;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedComment;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedCommentRepository;
import com.idolcollector.idolcollector.domain.notice.Notice;
import com.idolcollector.idolcollector.domain.notice.NoticeRepository;
import com.idolcollector.idolcollector.domain.notice.NoticeType;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentResponseDto;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class NestedCommentService {

    private final NestedCommentRepository nestedCommentRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final NoticeRepository noticeRepository;


    public NestedCommentResponseDto findById(Long id) {
        NestedComment nestedComment = nestedCommentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 대댓글입니다. id=" + id));

        return new NestedCommentResponseDto(nestedComment);
    }


    @Transactional
    public Long save(NestedCommentSaveRequestDto form) {
        Member member = memberRepository.findById(form.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. id=" + form.getAuthorId()));
        Comment comment = commentRepository.findById(form.getCommentId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다. id=" + form.getCommentId()));

        // 멤버는 세션에서 받아올 것.

        // Notice 만들기
        noticeRepository.save(new Notice(comment.getMember(), member, comment, NoticeType.COMMENT));

        NestedComment save = nestedCommentRepository.save(new NestedComment(member, comment, form.getContent()));
        return save.getId();
    }


    @Transactional
    public Long update(NestedCommentUpdateRequestDto form) {
        NestedComment nComment = nestedCommentRepository.findById(form.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 대댓글입니다. id=" + form.getId()));

        // 세션아이디 일치확인

        return nComment.update(form.getContent());
    }

    @Transactional
    public Long delete(Long id) {
        NestedComment nComment = nestedCommentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 대댓글입니다. id=" + id));

        // 세션아이디 일치확인

        nestedCommentRepository.delete(nComment);
        return id;
    }

    @Transactional
    public int like(Long id) {
        NestedComment nestedComment = nestedCommentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 대댓글입니다. id=" + id));

        // 세션 아이디 중복체크.

        return nestedComment.addLike();
    }

}
