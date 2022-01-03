package com.idolcollector.idolcollector.domain.scrap;

import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Scrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "SCRAP_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    private LocalDateTime createDate;

    public Scrap(Member member, Post post) {
        this.member = member;
        this.post = post;
        this.createDate = LocalDateTime.now();

        this.construct(member, post);
    }

    // 연관관게 메서드
    public void construct(Member member, Post post) {
        member.getScraps().add(this);
        post.getScraps().add(this);
    }
}
