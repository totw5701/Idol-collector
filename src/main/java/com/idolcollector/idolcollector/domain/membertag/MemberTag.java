package com.idolcollector.idolcollector.domain.membertag;

import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.tag.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class MemberTag {

    @Id
    @GeneratedValue
    @JoinColumn(name = "MEMBER_TAG_ID")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    public MemberTag(Member member, Tag tag) {
        this.member = member;
        this.tag = tag;
    }
}
