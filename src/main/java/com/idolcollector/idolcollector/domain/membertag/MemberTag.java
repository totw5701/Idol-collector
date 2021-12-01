package com.idolcollector.idolcollector.domain.membertag;

import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.tag.Tag;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class MemberTag {

    @Id
    @GeneratedValue
    @JoinColumn(name = "MEMBER_TAG_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tag tag;
}
