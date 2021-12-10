package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.domain.comment.CommentRepository;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedCommentRepository;
import com.idolcollector.idolcollector.domain.rank.Ranks;
import com.idolcollector.idolcollector.domain.rank.RanksRepository;
import com.idolcollector.idolcollector.web.dto.member.MemberResponseDto;
import com.idolcollector.idolcollector.web.dto.member.MemberSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.member.MemberUpdateRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired NestedCommentRepository nestedCommentRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired RanksRepository ranksRepository;


    @BeforeEach
    void before() {
        Ranks rank = new Ranks("ROLE_USER");
        ranksRepository.save(rank);
        Member member = new Member(rank, "nick", "email", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());
        memberRepository.save(member);
    }

    @Test
    void template() {

        // Given

        // When

        // Then

    }

    @Test
    void 회원가입_조회() {

        // Given
        MemberSaveRequestDto form = new MemberSaveRequestDto("nickName", "email@email.com", "1111", "mattew", "picture", LocalDateTime.now());

        // When
        Long memberId = memberService.join(form);

        // Then
        Member member = memberRepository.findById(memberId).get();

        assertThat(memberId).isEqualTo(member.getId());

    }

    @Test
    void 회원정보_수정() {

        // Given
        Member member = memberRepository.findAll().get(0);

        MemberUpdateRequestDto form = new MemberUpdateRequestDto(
                member.getId(),
                "update nickName",
                "update@email.com",
                "2222",
                "taylor",
                "salfhjdsahflh"
        );

        // When
        Long updateId = memberService.update(form);

        // Then
        Member update = memberRepository.findById(updateId).get();
        assertThat(update.getNickName()).isEqualTo("update nickName");
        assertThat(update.getEmail()).isEqualTo("update@email.com");
        assertThat(update.getPwd()).isEqualTo("2222");
        assertThat(update.getName()).isEqualTo("taylor");
        assertThat(update.getPicture()).isEqualTo("salfhjdsahflh");

    }

    @Test
    void 이메일조회() {

        // Given

        // When

        // Then
    }

}