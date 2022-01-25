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
public class CommentSaveRequestDto {

    @ApiModelProperty(value = "카드 id", example = "66", required = true)
    @NotNull
    private Long postId;

    @ApiModelProperty(value = "댓글 내용", example = "이하늬 예쁘네요 :)", required = true)
    @NotBlank
    private String content;

}
