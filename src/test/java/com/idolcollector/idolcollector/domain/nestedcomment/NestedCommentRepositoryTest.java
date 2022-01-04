package com.idolcollector.idolcollector.domain.nestedcomment;

import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.comment.CommentRepository;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.member.MemberRole;
import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class NestedCommentRepositoryTest {

    @Autowired private NestedCommentRepository nestedCommentRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private PostRepository postRepository;

    @BeforeEach
    void before() {
        Member member = new Member(MemberRole.USER, "qqqqeeeererwr#@#wr13", "email", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());
        memberRepository.save(member);
        Post post = new Post(member, "qqqqeeeererwr#@#wr13", "conten", "ste", "ori");
        postRepository.save(post);
        Comment comment = new Comment(member, post, "content");
        commentRepository.save(comment);
    }

    @Test
    void 저장_조회() {

        // Given
        Member member = memberRepository.findByNickName("qqqqeeeererwr#@#wr13").get();
        Comment comment = commentRepository.findAll().get(0);
        NestedComment nComment = new NestedComment(member, comment, "test");

        // When
        NestedComment save = nestedCommentRepository.save(nComment);
        NestedComment nestedComment = nestedCommentRepository.findById(save.getId()).get();

        // Then
        assertThat(nestedComment.getContent()).isEqualTo("test");
    }


    @Test
    void 수정() {

        //Given
        Member member = memberRepository.findByNickName("qqqqeeeererwr#@#wr13").get();
        Comment comment = commentRepository.findAll().get(0);
        NestedComment nComment = new NestedComment(member, comment, "test");
        NestedComment save = nestedCommentRepository.save(nComment);
        nestedCommentRepository.flush();

        NestedComment nestedComment = nestedCommentRepository.findById(save.getId()).get();

        //When
        nestedComment.update("updated");
        nestedCommentRepository.flush();

        NestedComment find = nestedCommentRepository.findById(save.getId()).get();

        //Then
        assertThat(find.getContent()).isEqualTo("updated");
    }


    @Test
    void 삭제() {

        //Given
        Member member = memberRepository.findByNickName("qqqqeeeererwr#@#wr13").get();
        Comment comment = commentRepository.findAll().get(0);
        NestedComment nComment = new NestedComment(member, comment, "test");
        NestedComment save = nestedCommentRepository.save(nComment);

        //When
        NestedComment nestedComment = nestedCommentRepository.findById(save.getId()).get();
        nestedCommentRepository.delete(nestedComment);

        Optional<NestedComment> find = nestedCommentRepository.findById(save.getId());

        //Then
        assertThat(find.isPresent()).isEqualTo(false);
    }


    @Test
    void 댓글_id로_대댓글_모두_가져오기() {
        // Given
        Member member = memberRepository.findByNickName("qqqqeeeererwr#@#wr13").get();

        Comment comment = commentRepository.findAllByMemberId(member.getId()).get(0);

        NestedComment nComment = new NestedComment(member, comment, "test");
        NestedComment nComment2 = new NestedComment(member, comment, "test2");

        NestedComment save = nestedCommentRepository.save(nComment);
        NestedComment save2 = nestedCommentRepository.save(nComment2);

        // When
        List<NestedComment> nComments = nestedCommentRepository.findAllInComment(comment.getId());

        // Then
        assertThat(nComments.size()).isEqualTo(2);
    }
}