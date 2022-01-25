package com.idolcollector.idolcollector.web.dto.bundle;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BundleSaveDto {

    @ApiModelProperty(value = "카드집 이름", example = "이하늬 타짜 모음", required = true)
    @NotBlank
    private String title;

    @ApiModelProperty(value = "카드집 설명", example = "이하늬 타짜2 나온 장면 직접 모은것들임", required = true)
    private String description;
}
