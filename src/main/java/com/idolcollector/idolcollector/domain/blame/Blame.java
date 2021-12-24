package com.idolcollector.idolcollector.domain.blame;

import com.idolcollector.idolcollector.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Blame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "BLAME_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "TARGET_MEMBER_ID")
    private Member targetMember;

    private Long targetId;

    @Enumerated(EnumType.STRING)
    private BlameTargetType type;

    private String message;
    private LocalDateTime createDate;


    public Blame(Member member, Member targetMember, Long targetId, BlameTargetType type, String message) {

        this.member = member;
        this.targetMember = targetMember;
        this.targetId = targetId;
        this.type = type;
        this.message = message;
        this.createDate = LocalDateTime.now();
    }
}
