package com.idolcollector.idolcollector.domain.member;

import com.idolcollector.idolcollector.domain.rank.Ranks;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue
    @JoinColumn(name = "MEMBER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RANKS_ID")
    private Ranks ranks;

    private String nickName;
    private String email;
    private String pwd;
    private String name;
    private String picture;
    private LocalDateTime dateOfBirth;
    private LocalDateTime joinDate;
    private LocalDateTime modifyDate;

    public Member(Ranks ranks, String nickName, String email, String pwd, String name, String picture, LocalDateTime dateOfBirth) {
        this.ranks = ranks;
        this.nickName = nickName;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.picture = picture;
        this.dateOfBirth = dateOfBirth;

        this.joinDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();
    }

}
