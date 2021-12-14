package com.idolcollector.idolcollector.domain.bundle;

import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Bundle {

    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    private String title;
    private String description;

    public Bundle(Member member, Post post, String title, String description) {
        this.member = member;
        this.post = post;
        this.title = title;
        this.description = description;

        construct(member);
    }

    // 연관관계 메소드
    private void construct(Member member) {
        member.getBundles().add(this);

        // post는 단방향으로 연결하였습니다.
    }
}
