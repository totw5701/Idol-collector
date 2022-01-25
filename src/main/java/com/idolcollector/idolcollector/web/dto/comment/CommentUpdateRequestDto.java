package com.idolcollector.idolcollector.web.dto.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateRequestDto {

    @ApiModelProperty(value = "댓글 id", example = "66", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "댓글 수정 내용", example = "이하늬 정말 예쁘네요", required = true)
    @NotBlank
    private String content;

}
