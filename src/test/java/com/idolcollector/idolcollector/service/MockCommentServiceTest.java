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
import com.idolcollector.idolcollector.domain.trending.Trending;
import com.idolcollector.idolcollector.domain.trending.TrendingRepository;
import com.idolcollector.idolcollector.web.dto.comment.CommentResponseDto;
import com.idolcollector.idolcollector.web.dto.comment.CommentSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.comment.CommentUpdateRequestDto;
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
public class MockCommentServiceTest {

    @InjectMocks
    CommentService commentService;

    @Mock NestedCommentRepository nestedCommentRepository;
    @Mock TrendingRepository trendingRepository;
    @Mock MemberRepository memberRepository;
    @Mock CommentRepository commentRepository;
    @Mock PostRepository postRepository;
    @Mock NoticeRepository noticeRepository;
    @Mock HttpSession httpSession;
    @Mock LikesRepository likesRepository;


    @Test
    void 저장_조회_성공() {
        // Given
        Member member = generateMember();
        Post post = generatePost(generateMember());
        CommentSaveRequestDto form = new CommentSaveRequestDto(post.getId(), "comment content");

        Optional<Member> memberOp = Optional.of(member);
        Optional<Post> postOp = Optional.of(post);

        doReturn(postOp).when(postRepository).findById(post.getId());
        doReturn(memberOp).when(memberRepository).findById(member.getId());
        doReturn(member.getId()).when(httpSession).getAttribute(any(String.class));
        doReturn(new Notice()).when(noticeRepository).save(any(Notice.class));
        doReturn(new Trending()).when(trendingRepository).save(any());
        doReturn(generateComment(post, member)).when(commentRepository).save(any(Comment.class));

        // When
        Long saveId = commentService.save(form);

        // Then

        boolean flag = false;
        for (Comment comment : post.getComments()) {
            if(comment.getContent().equals("comment content")) flag = true;
        }

        assertThat(flag).isTrue();
    }


    @Test
    void 수정_성공() {
        // Given
        Post post = generatePost(generateMember());
        Member member = generateMember();
        Comment comment = generateComment(post, member);
        CommentUpdateRequestDto form = new CommentUpdateRequestDto(comment.getId(), "updated comment content");

        Optional<Member> memberOp = Optional.of(member);
        Optional<Comment> commentOp = Optional.of(comment);

        doReturn(commentOp).when(commentRepository).findById(form.getId());
        doReturn(memberOp).when(memberRepository).findById(post.getMember().getId());
        doReturn(member.getId()).when(httpSession).getAttribute(any(String.class));

        // When
        Long id = commentService.update(form);

        // Then
        assertThat(comment.getContent()).isEqualTo("updated comment content");
    }


    @Test
    void 수정_실패() {
        // Given
        Post post = generatePost(generateMember());
        Member author = generateMember();
        author.test(2L);

        Comment comment = generateComment(post, author);
        CommentUpdateRequestDto form = new CommentUpdateRequestDto(comment.getId(), "updated comment content");


        Optional<Member> memberOp = Optional.of(post.getMember());
        Optional<Comment> commentOp = Optional.of(comment);

        doReturn(commentOp).when(commentRepository).findById(form.getId());
        doReturn(memberOp).when(memberRepository).findById(any());
        doReturn(author.getId()).when(httpSession).getAttribute(any(String.class));

        // When Then
        assertThatThrownBy(() ->{
            commentService.update(form);
        }).isInstanceOf(CAccessDeniedException.class);
    }


    @Test
    void 삭제() {
        // Given
        Post post = generatePost(generateMember());
        Member member = post.getMember();
        Comment comment = post.getComments().get(0);

        Optional<Member> memberOp = Optional.of(member);
        Optional<Comment> commentOp = Optional.of(comment);

        doReturn(commentOp).when(commentRepository).findById(comment.getId());
        doReturn(memberOp).when(memberRepository).findById(post.getMember().getId());
        doReturn(member.getId()).when(httpSession).getAttribute(any(String.class));
        doNothing().when(commentRepository).delete(any(Comment.class));

        // When
        commentService.delete(comment.getId());

        // Verify
        verify(commentRepository, times(1)).findById(any());
        verify(memberRepository, times(1)).findById(any());
        verify(httpSession, times(1)).getAttribute(any());
        verify(commentRepository, times(1)).delete(any());
    }


    @Test
    void 좋아요_성공() {
        // Given
        Member sessionMember = generateMember();
        sessionMember.test(1L);
        Member author = generateMember();
        author.test(2L);
        Post post = generatePost(generateMember());
        Comment comment = generateComment(post, author);

        Optional<Comment> commentOp = Optional.of(comment);
        Optional<Member> sessionOp = Optional.of(sessionMember);
        Optional<Likes> likeOp = Optional.empty();

        doReturn(commentOp).when(commentRepository).findById(any());
        doReturn(sessionOp).when(memberRepository).findById(1L);
        doReturn(1L).when(httpSession).getAttribute(any(String.class));
        doReturn(likeOp).when(likesRepository).findLikeByMemberIdPostId(any(), any(), any());
        doReturn(new Notice()).when(noticeRepository).save(any(Notice.class));
        doReturn(new Likes()).when(likesRepository).save(any(Likes.class));

        // When
        commentService.like(comment.getId());

        // Verify
        verify(commentRepository, times(1)).findById(any());
        verify(memberRepository, times(1)).findById(any());
        verify(httpSession, times(1)).getAttribute(any());
        verify(likesRepository, times(1)).findLikeByMemberIdPostId(any(), any(), any());
        verify(noticeRepository, times(1)).save(any());
        verify(likesRepository, times(1)).save(any());
    }


    @Test
    void 좋아요_실패() {
        // Given
        Member sessionMember = generateMember();
        sessionMember.test(1L);
        Member author = generateMember();
        author.test(2L);
        Post post = generatePost(generateMember());
        Comment comment = generateComment(post, author);

        Optional<Comment> commentOp = Optional.of(comment);
        Optional<Member> sessionOp = Optional.of(sessionMember);
        Optional<Likes> likeOp = Optional.of(new Likes());

        doReturn(commentOp).when(commentRepository).findById(any());
        doReturn(sessionOp).when(memberRepository).findById(1L);
        doReturn(1L).when(httpSession).getAttribute(any(String.class));
        doReturn(likeOp).when(likesRepository).findLikeByMemberIdPostId(any(), any(), any());

        // When
        assertThatThrownBy(() ->{
            commentService.like(comment.getId());
        }).isInstanceOf(IllegalArgumentException.class);
    }



    @Test
    void 게시글_댓글_받아오기() {
        // Given
        Member sessionMember = generateMember();
        sessionMember.test(1L);
        Member author = generateMember();
        author.test(2L);
        Post post = generatePost(generateMember());
        post.test(1L);

        Optional<Member> sessionOp = Optional.of(sessionMember);
        Optional<Likes> likesOp = Optional.empty();

        doReturn(post.getComments()).when(commentRepository).findAllByPostId(1L);
        doReturn(sessionOp).when(memberRepository).findById(1L);
        doReturn(1L).when(httpSession).getAttribute(any(String.class));
        doReturn(likesOp).when(likesRepository).findLikeByMemberIdPostId(any(), any(), any());

        // When
        List<CommentResponseDto> allInPost = commentService.findAllInPost(1L);

        // Then
        boolean flag1 = false;
        boolean flag2 = false;
        for (CommentResponseDto c : allInPost) {
            if(c.getContent().equals("comment1")) flag1 = true;
            if(c.getContent().equals("comment2")) flag2 = true;
        }

        assertThat(flag1).isTrue();
        assertThat(flag2).isTrue();
    }
}
