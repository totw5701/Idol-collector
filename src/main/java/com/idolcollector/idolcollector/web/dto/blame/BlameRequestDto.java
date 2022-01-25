package com.idolcollector.idolcollector.web.dto.blame;

import com.idolcollector.idolcollector.domain.blame.BlameTargetType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BlameRequestDto {

    @ApiModelProperty(value = "신고 대상 회원", example = "55", required = true)
    private Long targetMember;

    @ApiModelProperty(value = "신고 내용", example = "회원간의 분쟁을 만드는 게시물입니다.", required = true)
    private String message;

    @ApiModelProperty(value = "신고 대상 id", example = "66", required = true)
    private Long targetId;

    @ApiModelProperty(value = "신고 대상 종류", example = "POST", required = true)
    private BlameTargetType type;

}
