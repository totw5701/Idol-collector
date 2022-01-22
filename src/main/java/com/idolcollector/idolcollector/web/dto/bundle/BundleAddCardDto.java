package com.idolcollector.idolcollector.web.dto.bundle;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BundleAddCardDto {

    @ApiModelProperty(value = "카드 id", example = "66", required = true)
    private Long postId;

    @ApiModelProperty(value = "카드집 id", example = "3", required = true)
    private Long bundleId;

}
