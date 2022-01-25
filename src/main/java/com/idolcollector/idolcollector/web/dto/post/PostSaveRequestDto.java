package com.idolcollector.idolcollector.web.dto.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSaveRequestDto {

    @ApiModelProperty(value = "카드 제목", example = "이하늬 타짜2에서 섯다치는 모습", required = true)
    @NotBlank
    private String title;

    @ApiModelProperty(value = "카드 내용", example = "확실히 김혜수보다는 맛이 좀 떨어지는듯.", required = true)
    private String content;

    @ApiModelProperty(value = "이미지파일", required = true)
    @NotNull
    private MultipartFile attachFile;

    @ApiModelProperty(value = "태그", example = "이하늬", required = true)
    @Nullable
    private List<String> tags;

}
