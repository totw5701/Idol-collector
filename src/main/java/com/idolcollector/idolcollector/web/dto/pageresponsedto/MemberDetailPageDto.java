package com.idolcollector.idolcollector.web.dto.pageresponsedto;

import com.idolcollector.idolcollector.web.dto.bundle.BundleResponseDto;
import com.idolcollector.idolcollector.web.dto.member.MemberDetailDto;
import com.idolcollector.idolcollector.web.dto.post.HomePostListResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetailPageDto {

    private MemberDetailDto member;
    private List<BundleResponseDto> bundles;
    private List<HomePostListResponseDto> cards;
}
