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
import com.idolcollector.idolcollector.web.dto.bundle.*;
import com.idolcollector.idolcollector.web.dto.post.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BundleService {

    private final BundleRepository bundleRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final BundlePostRepository bundlePostRepository;
    private final HttpSession httpSession;

    public BundleResponseDto findById(Long id) {
        Bundle bundle = bundleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카드집입니다. id=" + id));

        return new BundleResponseDto(bundle);
    }

    public List<BundleResponseDto> findAllInMember(Long memberId) {

        List<Bundle> byMemberId = bundleRepository.findByMemberId(memberId);
        List<BundleResponseDto> list = new ArrayList<>();
        for (Bundle bundle : byMemberId) {
            list.add(new BundleResponseDto(bundle));
        }
        return list;
    }

    @Transactional
    public Long save(BundleSaveDto form) {
        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();

        Bundle save = bundleRepository.save(new Bundle(member, form.getTitle(), form.getDescription()));
        return save.getId();
    }

    @Transactional
    public Long delete(Long id) {
        Bundle bundle = bundleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카드집입니다. id=" + id));

        // 세션 회원과 일치 확인

        bundleRepository.delete(bundle);

        return id;
    }

    @Transactional
    public Long Update(BundleUpdateDto form) {
        Bundle bundle = bundleRepository.findById(form.getBundleId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카드집입니다. id=" + form.getBundleId()));

        return bundle.update(form);
    }

    @Transactional
    public Long addPost(BundleAddCardDto form) {
        Bundle bundle = bundleRepository.findById(form.getBundleId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카드집입니다. id=" + form.getBundleId()));

        Post post = postRepository.findById(form.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카드입니다. id=" + form.getPostId()));

        boolean present = bundlePostRepository.findByPostBundleId(post.getId(), bundle.getId()).isPresent();
        if (present) throw new IllegalArgumentException("이미 존재하는 카드입니다.");

        BundlePost save = bundlePostRepository.save(new BundlePost(bundle, post));
        return save.getId();
    }

    @Transactional
    public Long deletePost(BundleDeleteCardDto form) {
        BundlePost bundlePost = bundlePostRepository.findByPostBundleId(form.getPostId(), form.getBundleId())
                .orElseThrow(() -> new IllegalArgumentException("카드집 안에 카드가 들어있지 않습니다 id=" + form.getBundleId()));

        bundlePostRepository.delete(bundlePost);
        return bundlePost.getId();
    }

}
