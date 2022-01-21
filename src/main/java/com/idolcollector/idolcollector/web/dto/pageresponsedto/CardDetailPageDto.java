package com.idolcollector.idolcollector.web.dto.pageresponsedto;

import com.idolcollector.idolcollector.web.dto.member.MemberDetailDto;
import com.idolcollector.idolcollector.web.dto.post.PostResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CardDetailPageDto {

    private PostResponseDto card;
    private MemberDetailDto member;
}
