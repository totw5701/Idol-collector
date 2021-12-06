package com.idolcollector.idolcollector.domain.like;

import com.idolcollector.idolcollector.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Likes {

    @Id
    @GeneratedValue
    @JoinColumn(name = "LIKE_ID")
    private Long id;

    @ManyToOne
    private Member member;

    private Long targetId;

    @Enumerated(EnumType.STRING)
    private LikeType type;

    public Likes(Long targetId, Member member, LikeType type) {
        this.targetId = targetId;
        this.member = member;
        this.type = type;

        this.construct(member);
    }


    // 연관관계 메서드
    private void construct(Member member) {
        member.getLikesList().add(this);
    }
}
