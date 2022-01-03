package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.advice.exception.CCommentNotFoundException;
import com.idolcollector.idolcollector.advice.exception.CPostNotFoundException;
import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.comment.CommentRepository;
import com.idolcollector.idolcollector.domain.like.LikeType;
import com.idolcollector.idolcollector.domain.like.Likes;
import com.idolcollector.idolcollector.domain.like.LikesRepository;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.member.MemberRole;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedComment;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedCommentRepository;
import com.idolcollector.idolcollector.domain.notice.Notice;
import com.idolcollector.idolcollector.domain.notice.NoticeRepository;
import com.idolcollector.idolcollector.domain.notice.NoticeType;
import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.post.PostRepository;
import com.idolcollector.idolcollector.domain.trending.Trending;
import com.idolcollector.idolcollector.domain.trending.TrendingRepository;
import com.idolcollector.idolcollector.domain.trending.TrendingType;
import com.idolcollector.idolcollector.web.dto.comment.CommentResponseDto;
import com.idolcollector.idolcollector.web.dto.comment.CommentSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.comment.CommentUpdateRequestDto;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final NestedCommentRepository nestedCommentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final NoticeRepository noticeRepository;
    private final TrendingRepository trendingRepository;
    private final HttpSession httpSession;
    private final LikesRepository likesRepository;

    public CommentResponseDto findById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(CCommentNotFoundException::new);

        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();

        CommentResponseDto dto = new CommentResponseDto(comment);

        List<NestedComment> nComment = comment.getNComment();
        List<NestedCommentResponseDto> nCommentsDto = new ArrayList<>();
        for (NestedComment nestedComment : nComment) {
            NestedCommentResponseDto nestedCommentResponseDto = new NestedCommentResponseDto(nestedComment);
            Optional<Likes> didLikeN = likesRepository.findLikeByMemberIdPostId(nestedComment.getId(), member.getId(), LikeType.NESTED_COMMENT);
            if(didLikeN.isPresent()) nestedCommentResponseDto.didLike();
            nCommentsDto.add(nestedCommentResponseDto);
        }

        dto.setNCommentsDto(nCommentsDto);

        return dto;
    }

    @Transactional
    public Long save(CommentSaveRequestDto form) {

        Post post = postRepository.findById(form.getPostId())
                .orElseThrow(CPostNotFoundException::new);

        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();

        // Notice 만들기
        noticeRepository.save(new Notice(post.getMember(), member, post, NoticeType.COMMENT));

        // 추천 기록 테이블
        trendingRepository.save(new Trending(post, TrendingType.COMMENT));

        Comment save = commentRepository.save(new Comment(member, post, form.getContent()));
        return save.getId();
    }

    @Transactional
    public Long update(CommentUpdateRequestDto form) {
        Comment comment = commentRepository.findById(form.getId())
                .orElseThrow(CCommentNotFoundException::new);

        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();
        if (member.getId() != comment.getMember().getId()) {
            throw new IllegalArgumentException("작성자 본인만 수정할 수 있습니다. commentId = " + comment.getId());
        }

        return comment.update(form.getContent());
    }


    @Transactional
    public Long delete(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(CCommentNotFoundException::new);

        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();
        if (member.getId() != comment.getMember().getId()) {
            throw new IllegalArgumentException("작성자 본인만 삭제할 수 있습니다. commentId = " + comment.getId());
        }

        commentRepository.delete(comment);
        return comment.getId();
    }


    @Transactional
    public int like(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(CCommentNotFoundException::new);

        // 좋아요 유무 체크
        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();
        Optional<Likes> didLike = likesRepository.findLikeByMemberIdPostId(comment.getId(), member.getId(), LikeType.COMMENT);
        if(didLike.isPresent()) throw new IllegalArgumentException("이미 좋아요한 댓글입니다. commentId = " + comment.getId());

        // Notice 만들기
        noticeRepository.save(new Notice(comment.getMember(), member, comment.getPost(), comment, NoticeType.LIKE));

        //
        Likes likes = new Likes(comment.getId(), member, LikeType.COMMENT);
        likesRepository.save(likes);


        return comment.addLike();
    }

    public List<CommentResponseDto> findAllInPost(Long postId) {

        List<Comment> comments = commentRepository.findAllByPostId(postId);

        List<CommentResponseDto> list = new ArrayList<>();

        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();

        for (Comment comment : comments) {
            CommentResponseDto dto = new CommentResponseDto(comment);

            List<NestedComment> nComment = comment.getNComment();
            List<NestedCommentResponseDto> nCommentsDto = new ArrayList<>();
            for (NestedComment nestedComment : nComment) {
                NestedCommentResponseDto nestedCommentResponseDto = new NestedCommentResponseDto(nestedComment);
                Optional<Likes> didLikeN = likesRepository.findLikeByMemberIdPostId(nestedComment.getId(), member.getId(), LikeType.NESTED_COMMENT);
                if(didLikeN.isPresent()) nestedCommentResponseDto.didLike();
                nCommentsDto.add(nestedCommentResponseDto);
            }

            dto.setNCommentsDto(nCommentsDto);

            Optional<Likes> didLike = likesRepository.findLikeByMemberIdPostId(comment.getId(), member.getId(), LikeType.COMMENT);
            if(didLike.isPresent()) dto.didLike();

            list.add(dto);
        }

        return list;
    }


    // 사용하지 않음.
    public List<CommentResponseDto> findAllInMember(Long memberId) {

        List<Comment> comments = commentRepository.findAllByMemberId(memberId);

        List<CommentResponseDto> list = new ArrayList<>();

        for (Comment comment : comments) {
            list.add(new CommentResponseDto(comment));
        }

        return list;
    }
}
