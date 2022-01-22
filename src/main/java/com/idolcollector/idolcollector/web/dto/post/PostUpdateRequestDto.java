package com.idolcollector.idolcollector.web.dto.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateRequestDto {

    @ApiModelProperty(value = "카드 id", example = "66", required = true)
    @NotNull
    private Long postId;

    @ApiModelProperty(value = "카드 제목 수정", example = "이하늬 첫 장면", required = true)
    @NotBlank
    private String title;

    @ApiModelProperty(value = "카드 내용 수정", example = "이하늬 타짜 첫 등장신", required = true)
    private String content;

    @ApiModelProperty(value = "태그", example = "[\"tag1\", \"tag2\", ...]", required = true)
    @Nullable
    private List<String> tags;
}
