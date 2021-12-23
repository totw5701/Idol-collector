package com.idolcollector.idolcollector.web.dto.blame;

import com.idolcollector.idolcollector.domain.blame.BlameTargetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BlameRequestDto {

    private Long targetMember;
    private String message;
    private Long targetId;
    private BlameTargetType type;

}
