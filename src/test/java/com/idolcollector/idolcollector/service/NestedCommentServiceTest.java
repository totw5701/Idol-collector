package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.EntityMaker;
import com.idolcollector.idolcollector.advice.exception.CAccessDeniedException;
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
import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.post.PostRepository;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentResponseDto;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.idolcollector.idolcollector.EntityMaker.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NestedCommentServiceTest {

    @InjectMocks
    NestedCommentService nestedCommentService;

    @Mock NestedCommentRepository nestedCommentRepository;
    @Mock MemberRepository memberRepository;
    @Mock CommentRepository commentRepository;
    @Mock PostRepository postRepository;
    @Mock NoticeRepository noticeRepository;
    @Mock HttpSession httpSession;
    @Mock LikesRepository likesRepository;


    @Test
    void 저장_성공() {
        // Given
        Member sessionMember = generateMember();
        sessionMember.test(1L);
        Member author = generateMember();
        Post post = generatePost(generateMember());
        Comment comment = post.getComments().get(0);
        NestedComment nestedComment = generateNComment(comment, author);

        Optional<Member> sessionOp = Optional.of(sessionMember);
        Optional<Comment> commentOp = Optional.of(comment);

        doReturn(commentOp).when(commentRepository).findById(5L);
        doReturn(sessionOp).when(memberRepository).findById(1L);
        doReturn(1L).when(httpSession).getAttribute(any());
        doReturn(new Notice()).when(noticeRepository).save(any(Notice.class));
        doReturn(nestedComment).when(nestedCommentRepository).save(any());

        NestedCommentSaveRequestDto form = new NestedCommentSaveRequestDto(5L, "nComment content");

        // When
        nestedCommentService.save(form);

        // Verify
        verify(nestedCommentRepository, times(1)).save(any());
    }


    @Test
    void 수정_성공() {
        // Given
        Member sessionMember = generateMember();
        sessionMember.test(1L);
        Member author = generateMember();
        author.test(1L);
        Post post = generatePost(generateMember());
        Comment comment = post.getComments().get(0);
        NestedComment nestedComment = generateNComment(comment, author);

        Optional<Member> sessionOp = Optional.of(sessionMember);
        Optional<NestedComment> nCommentOp = Optional.of(nestedComment);

        doReturn(nCommentOp).when(nestedCommentRepository).findById(4L);
        doReturn(sessionOp).when(memberRepository).findById(1L);
        doReturn(1L).when(httpSession).getAttribute(any());

        NestedCommentUpdateRequestDto form = new NestedCommentUpdateRequestDto(4L, "updated n comment");

        // When
        nestedCommentService.update(form);

        // Then
        assertThat(nestedComment.getContent()).isEqualTo("updated n comment");
    }


    @Test
    void 수정_실패() {
        // Given
        Member sessionMember = generateMember();
        sessionMember.test(1L);
        Member author = generateMember();
        author.test(2L);
        Post post = generatePost(generateMember());
        Comment comment = post.getComments().get(0);
        NestedComment nestedComment = generateNComment(comment, author);

        Optional<Member> sessionOp = Optional.of(sessionMember);
        Optional<NestedComment> nCommentOp = Optional.of(nestedComment);

        doReturn(nCommentOp).when(nestedCommentRepository).findById(4L);
        doReturn(sessionOp).when(memberRepository).findById(1L);
        doReturn(1L).when(httpSession).getAttribute(any());

        NestedCommentUpdateRequestDto form = new NestedCommentUpdateRequestDto(4L, "updated n comment");

        // When

        assertThatThrownBy(() -> {
            nestedCommentService.update(form);
        }).isInstanceOf(CAccessDeniedException.class);

    }


    @Test
    void 삭제_성공() {
        // Given
        Member sessionMember = generateMember();
        sessionMember.test(1L);
        Member author = generateMember();
        author.test(1L);
        Post post = generatePost(generateMember());
        Comment comment = post.getComments().get(0);
        NestedComment nestedComment = generateNComment(comment, author);

        Optional<Member> sessionOp = Optional.of(sessionMember);
        Optional<NestedComment> nCommentOp = Optional.of(nestedComment);

        doReturn(nCommentOp).when(nestedCommentRepository).findById(4L);
        doReturn(sessionOp).when(memberRepository).findById(1L);
        doReturn(1L).when(httpSession).getAttribute(any());
        doNothing().when(nestedCommentRepository).delete(nestedComment);

        // When
        nestedCommentService.delete(4L);

        // Verify
        verify(nestedCommentRepository, times(1)).delete(any());
    }


    @Test
    void 삭제_실패() {
        // Given
        Member sessionMember = generateMember();
        sessionMember.test(1L);
        Member author = generateMember();
        author.test(2L);
        Post post = generatePost(generateMember());
        Comment comment = post.getComments().get(0);
        NestedComment nestedComment = generateNComment(comment, author);

        Optional<Member> sessionOp = Optional.of(sessionMember);
        Optional<NestedComment> nCommentOp = Optional.of(nestedComment);

        doReturn(nCommentOp).when(nestedCommentRepository).findById(4L);
        doReturn(sessionOp).when(memberRepository).findById(1L);
        doReturn(1L).when(httpSession).getAttribute(any());

        // When
        assertThatThrownBy(() -> {
            nestedCommentService.delete(4L);
        }).isInstanceOf(CAccessDeniedException.class);
    }


    @Test
    void 좋아요_성공() {
        // Given
        Member sessionMember = generateMember();
        sessionMember.test(1L);
        Member author = generateMember();
        author.test(1L);
        Post post = generatePost(generateMember());
        Comment comment = post.getComments().get(0);
        NestedComment nestedComment = generateNComment(comment, author);

        Optional<Member> sessionOp = Optional.of(sessionMember);
        Optional<NestedComment> nCommentOp = Optional.of(nestedComment);

        doReturn(nCommentOp).when(nestedCommentRepository).findById(4L);
        doReturn(sessionOp).when(memberRepository).findById(1L);
        doReturn(1L).when(httpSession).getAttribute(any());
        doReturn(new Likes()).when(likesRepository).save(any());

        // When
        nestedCommentService.like(4L);

        // Verify
        verify(likesRepository, times(1)).save(any());
    }


    @Test
    void 좋아요_실패() {
        // Given
        Member sessionMember = generateMember();
        sessionMember.test(1L);
        Member author = generateMember();
        author.test(1L);
        Post post = generatePost(generateMember());
        Comment comment = post.getComments().get(0);
        NestedComment nestedComment = generateNComment(comment, author);

        Optional<Member> sessionOp = Optional.of(sessionMember);
        Optional<NestedComment> nCommentOp = Optional.of(nestedComment);
        Optional<Likes> likesOp = Optional.of(new Likes());

        doReturn(nCommentOp).when(nestedCommentRepository).findById(4L);
        doReturn(sessionOp).when(memberRepository).findById(1L);
        doReturn(1L).when(httpSession).getAttribute(any());
        doReturn(likesOp).when(likesRepository).findLikeByMemberIdPostId(any(), any(), any());

        // When
        assertThatThrownBy(() -> {
            nestedCommentService.like(4L);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}