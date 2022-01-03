package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.advice.exception.CCommentNotFoundException;
import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.comment.CommentRepository;
import com.idolcollector.idolcollector.domain.like.LikeType;
import com.idolcollector.idolcollector.domain.like.Likes;
import com.idolcollector.idolcollector.domain.like.LikesRepository;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedComment;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedCommentRepository;
import com.idolcollector.idolcollector.domain.notice.Notice;
import com.idolcollector.idolcollector.domain.notice.NoticeRepository;
import com.idolcollector.idolcollector.domain.notice.NoticeType;
import com.idolcollector.idolcollector.domain.trending.Trending;
import com.idolcollector.idolcollector.domain.trending.TrendingType;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentResponseDto;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class NestedCommentService {

    private final NestedCommentRepository nestedCommentRepository;
    private final CommentRepository commentRepository;
    private final NoticeRepository noticeRepository;
    private final HttpSession httpSession;
    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;

    public NestedCommentResponseDto findById(Long id) {
        NestedComment nestedComment = nestedCommentRepository.findById(id)
                .orElseThrow(CCommentNotFoundException::new);

        return new NestedCommentResponseDto(nestedComment);
    }


    @Transactional
    public Long save(NestedCommentSaveRequestDto form) {
        Comment comment = commentRepository.findById(form.getCommentId())
                .orElseThrow(CCommentNotFoundException::new);

        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();

        // Notice 만들기
        noticeRepository.save(new Notice(comment.getMember(), member, comment.getPost(), comment, NoticeType.COMMENT));

        NestedComment save = nestedCommentRepository.save(new NestedComment(member, comment, form.getContent()));
        return save.getId();
    }


    @Transactional
    public Long update(NestedCommentUpdateRequestDto form) {
        NestedComment nComment = nestedCommentRepository.findById(form.getId())
                .orElseThrow(CCommentNotFoundException::new);

        // 세션아이디 일치확인
        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();
        if(member.getId() != nComment.getMember().getId()) throw new IllegalArgumentException("작성자만 수정할 수 있습니다. 대댓글 id=" + form.getId());

        return nComment.update(form.getContent());
    }

    @Transactional
    public Long delete(Long id) {
        NestedComment nComment = nestedCommentRepository.findById(id)
                .orElseThrow(CCommentNotFoundException::new);

        // 세션아이디 일치확인
        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();
        if(member.getId() != nComment.getMember().getId()) throw new IllegalArgumentException("작성자만 수정할 수 있습니다. 대댓글 id=" + id);

        nestedCommentRepository.delete(nComment);
        return id;
    }

    @Transactional
    public int like(Long id) {
        NestedComment nestedComment = nestedCommentRepository.findById(id)
                .orElseThrow(CCommentNotFoundException::new);

        // 세션 아이디 중복체크.
        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();
        Optional<Likes> didLike = likesRepository.findLikeByMemberIdPostId(nestedComment.getId(), member.getId(), LikeType.NESTED_COMMENT);
        if(didLike.isPresent()) throw new IllegalArgumentException("이미 좋아요한 대댓글입니다. commentId = " + nestedComment.getId());

        // 좋아요
        likesRepository.save(new Likes(id, member, LikeType.NESTED_COMMENT));

        return nestedComment.addLike();
    }

}
