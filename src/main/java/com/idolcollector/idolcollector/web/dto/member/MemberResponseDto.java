package com.idolcollector.idolcollector.web.dto.member;

import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.rank.Ranks;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private String roll;

    private String nickName;
    private String email;
    private String name;
    private String picture;
    private LocalDateTime dateOfBirth;
    private LocalDateTime joinDate;
    private LocalDateTime modifyDate;

    public MemberResponseDto(Member member) {
        this.roll = member.getRanks().getRoll();
        this.nickName = member.getNickName();
        this.email = member.getEmail();
        this.name = member.getName();
        this.picture = member.getPicture();
        this.dateOfBirth = member.getDateOfBirth();
        this.joinDate = member.getJoinDate();
        this.modifyDate = member.getModifyDate();
    }
}
