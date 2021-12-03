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
    @GeneratedValue
    @JoinColumn(name = "SCRAP_ID")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "POST_ID")
    private Post post;

    private LocalDateTime createDate;

    public Scrap(Member member, Post post) {
        this.member = member;
        this.post = post;
        this.createDate = LocalDateTime.now();
    }
}
