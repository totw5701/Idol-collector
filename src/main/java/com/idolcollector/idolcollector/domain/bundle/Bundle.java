package com.idolcollector.idolcollector.domain.bundle;

import com.idolcollector.idolcollector.domain.bindlepost.BundlePost;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.web.dto.bundle.BundleUpdateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Bundle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String title;
    private String description;

    @OneToMany(mappedBy = "bundle", orphanRemoval = true)
    private List<BundlePost> bundlePosts = new ArrayList<>();

    public Bundle(Member member, String title, String description) {
        this.member = member;
        this.title = title;
        this.description = description;

        construct(member);
    }

    // 연관관계 메소드
    private void construct(Member member) {
        member.getBundles().add(this);
    }

    // 비즈니스 메소드
    public Long update(BundleUpdateDto form) {
        this.title = form.getTitle();
        this.description = form.getDescription();

        return this.id;
    }
}
