package com.idolcollector.idolcollector.web.dto.response;

import com.idolcollector.idolcollector.web.dto.post.PostResponseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult<T> {

    @ApiModelProperty(value = "성공여부", example = "true", required = true)
    private boolean success;

    // 0 <= 정상, 0 > 비정상
    @ApiModelProperty(value = "code", example = "0 <= 정상, 0 > 비정상", required = true)
    private int code;

    @ApiModelProperty(value = "메세지", example = "성공했습니다.", required = true)
    private String msg;

    private T data;
}
