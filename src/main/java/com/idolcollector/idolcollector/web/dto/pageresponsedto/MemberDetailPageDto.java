package com.idolcollector.idolcollector.web.dto.pageresponsedto;

import com.idolcollector.idolcollector.web.dto.bundle.BundleResponseDto;
import com.idolcollector.idolcollector.web.dto.member.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetailPageDto {

    private MemberResponseDto member;
    private BundleResponseDto bundles;
}
