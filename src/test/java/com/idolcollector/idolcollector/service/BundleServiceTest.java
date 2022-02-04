package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.advice.exception.CBundleNotFoundException;
import com.idolcollector.idolcollector.domain.bindlepost.BundlePost;
import com.idolcollector.idolcollector.domain.bindlepost.BundlePostRepository;
import com.idolcollector.idolcollector.domain.bundle.Bundle;
import com.idolcollector.idolcollector.domain.bundle.BundleRepository;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.post.PostRepository;
import com.idolcollector.idolcollector.web.dto.bundle.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.idolcollector.idolcollector.EntityMaker.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BundleServiceTest {

    @InjectMocks
    BundleService bundleService;

    @Mock MemberRepository memberRepository;
    @Mock PostRepository postRepository;
    @Mock BundleRepository bundleRepository;
    @Mock BundlePostRepository bundlePostRepository;
    @Mock HttpSession httpSession;

    @Test
    void 조회_성공() {
        // Given
        Member member = generateMember();
        Bundle bundle = generateBundle(member);

        Optional<Bundle> bundleOp = Optional.of(bundle);

        doReturn(bundleOp).when(bundleRepository).findById(any());

        // When
        BundleResponseDto result = bundleService.findById(1L);

        // Then
        assertThat(result.getTitle()).isEqualTo(bundle.getTitle());
        assertThat(result.getDescription()).isEqualTo(bundle.getDescription());
    }


    @Test
    void 조회_실패() {
        // Given
        Member member = generateMember();
        Bundle bundle = generateBundle(member);

        Optional<Bundle> bundleOp = Optional.empty();

        doReturn(bundleOp).when(bundleRepository).findById(any());

        // When
        assertThatThrownBy(() -> {
            bundleService.findById(1L);
        }).isInstanceOf(CBundleNotFoundException.class);
    }


    @Test
    void 카드집_생성() {
        // Given
        Member sessionMember = generateMember();
        sessionMember.test(1L);
        Bundle bundle = generateBundle(sessionMember);


        Optional<Member> memberOp = Optional.of(sessionMember);

        doReturn(memberOp).when(memberRepository).findById(sessionMember.getId());
        doReturn(sessionMember.getId()).when(httpSession).getAttribute(any(String.class));
        doReturn(bundle).when(bundleRepository).save(any());

        BundleSaveDto form = new BundleSaveDto(bundle.getTitle(), bundle.getDescription());

        // When
        bundleService.save(form);

        // Verify
        verify(bundleRepository, times(1)).save(any());
    }


    @Test
    void 삭제() {
        // Given
        Member member = generateMember();
        Bundle bundle = generateBundle(member);

        Optional<Bundle> bundleOp = Optional.of(bundle);

        doReturn(bundleOp).when(bundleRepository).findById(any());
        doNothing().when(bundleRepository).delete(any(Bundle.class));

        // When
        bundleService.delete(1L);

        // Verify
        verify(bundleRepository, times(1)).delete(any());
    }


    @Test
    void 카드집_수정() {
        // Given
        Member member = generateMember();
        Bundle bundle = generateBundle(member);

        Optional<Bundle> bundleOp = Optional.of(bundle);

        doReturn(bundleOp).when(bundleRepository).findById(1L);

        BundleUpdateDto form = new BundleUpdateDto(1L, "updated title", "updated desc");

        // When
        bundleService.update(form);

        // Then
        assertThat(bundle.getTitle()).isEqualTo("updated title");
        assertThat(bundle.getDescription()).isEqualTo("updated desc");
    }


    @Test
    void 카드_추가() {
        // Given
        Member member = generateMember();
        Bundle bundle = generateBundle(member);
        Post post = generatePost(member);

        Optional<Bundle> bundleOp = Optional.of(bundle);
        Optional<Post> postOp = Optional.of(post);
        Optional<BundlePost> bundlePostOp = Optional.empty();

        doReturn(bundleOp).when(bundleRepository).findById(any());
        doReturn(postOp).when(postRepository).findById(any());
        doReturn(bundlePostOp).when(bundlePostRepository).findByPostBundleId(any(), any());
        doReturn(new BundlePost()).when(bundlePostRepository).save(any());

        BundleAddCardDto form = new BundleAddCardDto(1L, 1L);

        // When
        bundleService.addPost(form);

        // Verify
        verify(bundlePostRepository, times(1)).save(any());
    }


    @Test
    void 카드_삭제() {
        // Given
        Optional<BundlePost> bundlePostOp = Optional.of(new BundlePost());

        doReturn(bundlePostOp).when(bundlePostRepository).findByPostBundleId(1L, 1L);
        doNothing().when(bundlePostRepository).delete(any(BundlePost.class));

        BundleDeleteCardDto form = new BundleDeleteCardDto(1L, 1L);

        // When
        bundleService.deletePost(form);

        // Verify
        verify(bundlePostRepository, times(1)).delete(any(BundlePost.class));
    }


    @Test
    void 회원_카드집_조회() {
        // Given
        Member member = generateMember();
        member.test(1L);

        List<Bundle> list = new ArrayList<>();
        list.add(new Bundle(member, "ttl1", "desc1"));
        list.add(new Bundle(member, "ttl2", "desc2"));

        doReturn(list).when(bundleRepository).findByMemberId(1L);

        // When
        List<BundleResponseDto> bundles = bundleService.findAllInMember(1L);

        // Then
        for (BundleResponseDto b : bundles) {
            assertThat(b.getTitle()).contains("ttl");
            assertThat(b.getDescription()).contains("desc");
        }
    }
}