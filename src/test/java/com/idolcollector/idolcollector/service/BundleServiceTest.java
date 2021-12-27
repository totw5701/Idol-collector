package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.domain.bindlepost.BundlePost;
import com.idolcollector.idolcollector.domain.bindlepost.BundlePostRepository;
import com.idolcollector.idolcollector.domain.bundle.Bundle;
import com.idolcollector.idolcollector.domain.bundle.BundleRepository;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.member.MemberRole;
import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.post.PostRepository;
import com.idolcollector.idolcollector.web.dto.bundle.BundleAddCardDto;
import com.idolcollector.idolcollector.web.dto.bundle.BundleDeleteCardDto;
import com.idolcollector.idolcollector.web.dto.bundle.BundleSaveDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BundleServiceTest {

    @Autowired
    BundleService bundleService;

    @Autowired MemberRepository memberRepository;
    @Autowired PostRepository postRepository;
    @Autowired BundleRepository bundleRepository;
    @Autowired BundlePostRepository bundlePostRepository;
    @Autowired HttpSession httpSession;

    @BeforeEach
    void before() {
        Member member = new Member(MemberRole.USER, "nick", "email", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());
        memberRepository.save(member);
        Post post = new Post(member, "title", "conten", "ste", "ori");
        postRepository.save(post);
    }

    @Test
    void 조회() {

        // Given

        // When

        // Then
    }

    @Test
    void 카드_삭제() {

        // Given
        Member member = memberRepository.findAll().get(0);
        Post post = postRepository.findAll().get(0);

        Bundle bundle = new Bundle(member, "title", "desc");
        Bundle save = bundleRepository.save(bundle);

        bundlePostRepository.save(new BundlePost(bundle, post));

        BundleDeleteCardDto form = new BundleDeleteCardDto(post.getId(), bundle.getId());

        // When
        bundleService.deletePost(form);

        // Then
        List<BundlePost> all = bundlePostRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }


    @Test
    void 카드_추가() {

        // Given
        Member member = memberRepository.findAll().get(0);
        Post post = postRepository.findAll().get(0);

        Bundle bundle = new Bundle(member, "title", "desc");
        Bundle save = bundleRepository.save(bundle);

        BundleAddCardDto form = new BundleAddCardDto(post.getId(), bundle.getId());

        // When
        Long saved = bundleService.addPost(form);

        // Then
        List<BundlePost> all = bundlePostRepository.findAll();
        assertThat(all.size()).isEqualTo(1);

    }

    @Test
    void 카드집_생성() {

        // Given
        Member member = memberRepository.findAll().get(0);
        httpSession.setAttribute("loginMember", member.getId());


        BundleSaveDto form = new BundleSaveDto("title", "description");

        // When
        Long save = bundleService.save(form);

        // Then
        Bundle bundle = bundleRepository.findById(save).get();

        assertThat(bundle.getTitle()).isEqualTo("title");

    }

    @Test
    void 삭제() {

        // Given
        Member member = memberRepository.findAll().get(0);
        Post post = postRepository.findAll().get(0);

        Bundle bundle = new Bundle(member, "title", "desc");
        Bundle save = bundleRepository.save(bundle);

        // When
        bundleService.delete(save.getId());

        // Then
        Optional<Bundle> byId = bundleRepository.findById(save.getId());
        assertThat(byId.isPresent()).isEqualTo(false);
    }

}