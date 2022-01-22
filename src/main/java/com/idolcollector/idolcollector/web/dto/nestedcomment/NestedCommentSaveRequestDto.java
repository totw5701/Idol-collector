package com.idolcollector.idolcollector.web.dto.nestedcomment;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NestedCommentSaveRequestDto {

    @ApiModelProperty(value = "댓글 id", example = "66", required = true)
    @NotNull
    private Long commentId;

    @ApiModelProperty(value = "대댓글 내용", example = "대댓글 내용입니다.", required = true)
    @NotBlank
    private String content;

}
