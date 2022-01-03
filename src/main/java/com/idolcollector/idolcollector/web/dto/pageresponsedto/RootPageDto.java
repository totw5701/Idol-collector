package com.idolcollector.idolcollector.web.dto.pageresponsedto;

import com.idolcollector.idolcollector.web.dto.member.MemberResponseDto;
import com.idolcollector.idolcollector.web.dto.post.HomePostListResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RootPageDto {

    private List<HomePostListResponseDto> cards = new ArrayList<>();
    private MemberResponseDto member;
}
