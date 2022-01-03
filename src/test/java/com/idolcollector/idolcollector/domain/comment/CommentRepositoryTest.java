package com.idolcollector.idolcollector.domain.comment;

import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.member.MemberRole;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedCommentRepository;
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

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CommentRepositoryTest {

    @Autowired NestedCommentRepository nestedCommentRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired PostRepository postRepository;

    @BeforeEach
    void before() {
        Member member = new Member(MemberRole.USER, "nick", "email", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());
        memberRepository.save(member);

        Post post = new Post(member, "title", "conten", "ste", "ori");
        postRepository.save(post);

    }

    @Test
    void 저장_조회() {

        // Given
        Member member = memberRepository.findAll().get(0);
        Post post = postRepository.findAll().get(0);
        Comment comment = new Comment(member, post, "content");

        // When
        commentRepository.save(comment);
        Comment find = commentRepository.findById(comment.getId()).get();

        // Then
        assertThat(find.getContent()).isEqualTo("content");
    }


    @Test
    void 수정() {

        //

    }


    @Test
    void 삭제() {

        // Given
        Member member = memberRepository.findAll().get(0);
        Post post = postRepository.findAll().get(0);
        Comment comment = new Comment(member, post, "content");

        commentRepository.save(comment);
        Comment find = commentRepository.findById(comment.getId()).get();

        // When
        commentRepository.delete(find);
        commentRepository.flush();
        Optional<Comment> result = commentRepository.findById(comment.getId());

        // Then
        assertThat(result.isPresent()).isEqualTo(false);
    }


    @Test
    void post_id로_댓글_모두_가져오기() {

        // Given
        Member member = memberRepository.findAll().get(0);
        Post post = postRepository.findAll().get(0);

        Comment comment = new Comment(member, post, "content");
        Comment comment2 = new Comment(member, post, "content");

        commentRepository.save(comment);
        commentRepository.save(comment2);

        // When
        List<Comment> comments = commentRepository.findAllByPostId(post.getId());

        // Then
        assertThat(comments.size()).isEqualTo(2);
    }

    @Test
    void 회원의_댓글_모두_가져오기() {
        // Given
        Member member = memberRepository.findAll().get(0);
        Post post = postRepository.findAll().get(0);

        Comment comment = new Comment(member, post, "content");
        Comment comment2 = new Comment(member, post, "content");

        commentRepository.save(comment);
        commentRepository.save(comment2);

        // When
        List<Comment> comments = commentRepository.findAllByMemberId(member.getId());

        // Then
        assertThat(comments.size()).isEqualTo(2);
    }


}