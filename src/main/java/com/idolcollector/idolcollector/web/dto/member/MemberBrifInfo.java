package com.idolcollector.idolcollector.web.dto.member;

import com.idolcollector.idolcollector.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberBrifInfo {

    private Long id;
    private String nickName;
    private String picture;

    public MemberBrifInfo(MemberDetailDto member){
        this.id = member.getId();
        this.nickName = member.getNickName();
        this.picture = member.getPicture();
    }

    public MemberBrifInfo(Member member) {
        this.id = member.getId();
        this.nickName = member.getNickName();
        this.picture = member.getPicture();
    }

}
