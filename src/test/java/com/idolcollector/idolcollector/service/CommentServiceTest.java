package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.comment.CommentRepository;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.member.MemberRole;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedComment;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedCommentRepository;
import com.idolcollector.idolcollector.domain.notice.Notice;
import com.idolcollector.idolcollector.domain.notice.NoticeRepository;
import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.post.PostRepository;
import com.idolcollector.idolcollector.web.dto.comment.CommentResponseDto;
import com.idolcollector.idolcollector.web.dto.comment.CommentSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.comment.CommentUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    NestedCommentRepository nestedCommentRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    NoticeRepository noticeRepository;

    @BeforeEach
    void before() {
        Member member = new Member(MemberRole.ROLE_USER, "nick", "email", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());
        memberRepository.save(member);
        Post post = new Post(member, "title", "conten", "ste", "ori");
        postRepository.save(post);

    }

    @Test
    void 저장_조회() {

        // Given
        Member member = memberRepository.findAll().get(0);
        Post post = postRepository.findAll().get(0);

        CommentSaveRequestDto form = new CommentSaveRequestDto(member.getId(), post.getId(), "comment content");

        // When
        Long saveId = commentService.save(form);
            // 대댓글 작성
        Comment comment = commentRepository.findById(saveId).get();
        nestedCommentRepository.save(new NestedComment(member, comment, "n Comment"));

        CommentResponseDto find = commentService.findById(saveId);

        // Then
        assertThat(find.getId()).isEqualTo(saveId);
        assertThat(find.getNestedComments().get(0).getContent()).isEqualTo("n Comment");

        List<Notice> notices = noticeRepository.findAll();
        assertThat(notices.size()).isEqualTo(1);
    }


    @Test
    void 수정() {

        // Given
        Member member = memberRepository.findAll().get(0);
        Post post = postRepository.findAll().get(0);
        Comment comment = commentRepository.save(new Comment(member, post, "comment content"));

        CommentUpdateRequestDto form = new CommentUpdateRequestDto(comment.getId(), "updated comment content");

        // When
        Long updateId = commentService.update(form);
        Comment find = commentRepository.findById(updateId).get();

        // Then
        assertThat(find.getContent()).isEqualTo("updated comment content");

    }


    @Test
    void 삭제() {

        // Given
        Member member = memberRepository.findAll().get(0);
        Post post = postRepository.findAll().get(0);
        Comment comment = commentRepository.save(new Comment(member, post, "comment content"));

        NestedComment n_comment = nestedCommentRepository.save(new NestedComment(member, comment, "n Comment"));

        // When
        Long deleteId = commentService.delete(comment.getId());

        assertThatThrownBy(() -> {
            commentRepository.findById(deleteId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 입니다."));
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 댓글 입니다.");
    }


    @Test
    void 좋아요() {

        // Given
        Member member = memberRepository.findAll().get(0);
        Post post = postRepository.findAll().get(0);
        Comment comment = commentRepository.save(new Comment(member, post, "comment content"));

        // When
        comment.addLike();

        // Then
        assertThat(comment.getLikes()).isEqualTo(1);

        List<Notice> notices = noticeRepository.findAll();
        assertThat(notices.size()).isEqualTo(1);
    }

    @Test
    void 게시글_댓글_받아오기() {

        // Given
        Member member = memberRepository.findAll().get(0);
        Post post = postRepository.findAll().get(0);

        Comment comment = new Comment(member, post, "content");
        Comment comment2 = new Comment(member, post, "content");

        commentRepository.save(comment);
        commentRepository.save(comment2);

        NestedComment nComment = new NestedComment(member, comment, "nContent");

        nestedCommentRepository.save(nComment);


        // When
        List<CommentResponseDto> comments = commentService.findAllInPost(post.getId());

        // Then
        assertThat(comments.size()).isEqualTo(2);
        assertThat(comments.get(0).getContent()).isEqualTo("content");
        assertThat(comments.get(0).getNestedComments().get(0).getContent()).isEqualTo("nContent");
    }
}
