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
public class NestedCommentUpdateRequestDto {

    @ApiModelProperty(value = "대댓글 id", example = "66", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "수정 내용", example = "대댓글을 수정합시다.", required = true)
    @NotBlank
    private String content;

}
