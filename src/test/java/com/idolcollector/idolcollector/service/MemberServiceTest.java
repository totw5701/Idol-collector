package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.domain.comment.CommentRepository;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedCommentRepository;
import com.idolcollector.idolcollector.domain.rank.Ranks;
import com.idolcollector.idolcollector.domain.rank.RanksRepository;
import com.idolcollector.idolcollector.web.dto.member.MemberResponseDto;
import com.idolcollector.idolcollector.web.dto.member.MemberSaveRequestDto;
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

    @Autowired private NestedCommentRepository nestedCommentRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private RanksRepository ranksRepository;

    @BeforeEach
    void before() {
        Ranks rank = new Ranks("ROLL_USER");
        ranksRepository.save(rank);
        ranksRepository.flush();
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

        // When

        // Then

    }

    @Test
    void 닉네임조회() {

        // Given

        // When

        // Then
    }

    @Test
    void 이메일조회() {

        // Given

        // When

        // Then
    }

}