package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.comment.CommentRepository;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedCommentRepository;
import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.rank.Ranks;
import com.idolcollector.idolcollector.domain.rank.RanksRepository;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentResponseDto;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentSaveRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class NestedCommentServiceTest {

    @Autowired
    private NestedCommentService nestedCommentService;

    @Autowired private NestedCommentRepository nestedCommentRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private RanksRepository ranksRepository;


    @Test
    void template() {

        // Given

        // When

        // Then

    }

    @Test
    void 저장_조회() {

        // Given
        Member member = new Member(new Ranks(), "nick", "email", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());
        memberRepository.save(member);
        Comment comment = new Comment(member, new Post(), "content");
        commentRepository.save(comment);

        NestedCommentSaveRequestDto form = new NestedCommentSaveRequestDto(member.getId(), comment.getId(), "nest comment test");

        // When
        Long save = nestedCommentService.save(form);
        NestedCommentResponseDto nComment = nestedCommentService.findById(save);

        // Then
        assertThat(nComment.getContent()).isEqualTo("nest comment test");

    }



    @Test
    void 모든_NestedComment_받아오기() {

        // Given
        Ranks rank = new Ranks("ROLL_USER");
        ranksRepository.save(rank);
        Member member = new Member(rank, "nick", "email", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());
        memberRepository.save(member);
        Comment comment = new Comment(member, new Post(), "content");
        commentRepository.save(comment);

        NestedCommentSaveRequestDto form = new NestedCommentSaveRequestDto(member.getId(), comment.getId(), "nest comment test111");
        NestedCommentSaveRequestDto form2 = new NestedCommentSaveRequestDto(member.getId(), comment.getId(), "nest comment test222");

        Long save = nestedCommentService.save(form);
        Long save2 = nestedCommentService.save(form2);

        // When
        List<NestedCommentResponseDto> nComments = nestedCommentService.findAllInComment(comment.getId());

        // Then
        assertThat(nComments.size()).isEqualTo(2);
    }
}