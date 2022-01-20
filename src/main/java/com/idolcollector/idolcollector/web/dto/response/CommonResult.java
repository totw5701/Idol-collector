package com.idolcollector.idolcollector.web.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult<T> {

    private boolean success;

    // 0 <= 정상, 0 > 비정상
    private int code;

    private String msg;

    private T data;
}
