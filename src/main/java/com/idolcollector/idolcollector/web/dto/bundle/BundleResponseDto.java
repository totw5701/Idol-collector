package com.idolcollector.idolcollector.web.dto.bundle;

import com.idolcollector.idolcollector.domain.bindlepost.BundlePost;
import com.idolcollector.idolcollector.domain.bundle.Bundle;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.web.dto.post.HomePostListResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BundleResponseDto {

    private Long id;

    private String memberNickName;
    private String title;
    private String description;
    private List<HomePostListResponseDto> posts = new ArrayList<>();


    public BundleResponseDto(Bundle bundle) {

        this.id = bundle.getId();
        this.memberNickName = bundle.getMember().getNickName();
        this.title = bundle.getTitle();
        this.description = bundle.getDescription();

        List<BundlePost> bundlePosts = bundle.getBundlePosts();
        for (BundlePost bundlePost : bundlePosts) {
            posts.add(new HomePostListResponseDto(bundlePost.getPost()));
        }
    }

}
