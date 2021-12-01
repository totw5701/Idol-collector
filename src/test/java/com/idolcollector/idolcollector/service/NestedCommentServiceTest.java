package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.comment.CommentRepository;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedComment;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedCommentRepository;
import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.post.PostRepository;
import com.idolcollector.idolcollector.domain.rank.Ranks;
import com.idolcollector.idolcollector.domain.rank.RanksRepository;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentResponseDto;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class NestedCommentServiceTest {

    @Autowired NestedCommentService nestedCommentService;

    @Autowired NestedCommentRepository nestedCommentRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired RanksRepository ranksRepository;
    @Autowired PostRepository postRepository;

    @BeforeEach
    void before() {
        Ranks rank = new Ranks("ROLL_USER");
        ranksRepository.save(rank);
        Member member = new Member(rank, "nick", "email", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());
        memberRepository.save(member);
        Post post = new Post(member, "title", "conten", "ste", "ori");
        postRepository.save(post);
        Comment comment = new Comment(member, post, "content");
        commentRepository.save(comment);
    }

    @Test
    void 저장_조회() {

        // Given
        Member member = memberRepository.findAll().get(0);
        Comment comment = commentRepository.findAll().get(0);

        NestedCommentSaveRequestDto form = new NestedCommentSaveRequestDto(member.getId(), comment.getId(), "nest comment test");

        // When
        Long save = nestedCommentService.save(form);
        NestedCommentResponseDto nComment = nestedCommentService.findById(save);

        // Then
        assertThat(nComment.getContent()).isEqualTo("nest comment test");
    }


    @Test
    void 수정() {

        // Given
        Member member = memberRepository.findAll().get(0);
        Comment comment = commentRepository.findAll().get(0);
        NestedComment saved = nestedCommentRepository.save(new NestedComment(member, comment, "nest comment test"));

        NestedCommentUpdateRequestDto form = new NestedCommentUpdateRequestDto(saved.getId(), "updated nest comment test");

        // When
        Long updatedId = nestedCommentService.update(form);
        NestedComment result = nestedCommentRepository.findById(updatedId).get();

        // Then
        assertThat(result.getContent()).isEqualTo("updated nest comment test");
        assertThat(result.getCreateDate()).isNotEqualTo(result.getModifyDate());
    }


    @Test
    void 삭제() {

        // Given
        Member member = memberRepository.findAll().get(0);
        Comment comment = commentRepository.findAll().get(0);
        NestedComment saved = nestedCommentRepository.save(new NestedComment(member, comment, "nest comment test"));

        // When
        nestedCommentService.delete(saved.getId());
        List<NestedComment> result = nestedCommentRepository.findAll();

        // Then
        assertThat(result.size()).isEqualTo(0);
    }


    @Test
    void 좋아요() {

        // Given
        Member member = memberRepository.findAll().get(0);
        Comment comment = commentRepository.findAll().get(0);
        NestedComment nComment = nestedCommentRepository.save(new NestedComment(member, comment, "nest comment test"));

        // When
        nComment.addLike();

        NestedComment find = nestedCommentRepository.findById(nComment.getId()).get();

        // Then
        assertThat(find.getLikes()).isEqualTo(1);
    }
}