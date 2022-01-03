package com.idolcollector.idolcollector.domain.bindlepost;

import com.idolcollector.idolcollector.domain.bundle.Bundle;
import com.idolcollector.idolcollector.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class BundlePost {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bundle_id")
    private Bundle bundle;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public BundlePost(Bundle bundle, Post post) {
        this.bundle = bundle;
        this.post = post;

        construct(bundle);
    }

    // 연관관계메소드
    private void construct(Bundle bundle) {
        bundle.getBundlePosts().add(this);
        // post와는 단방향연관관계입니다. post에서 bundle을 조회할 일이 없음.
    }
}
