package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.web.dto.response.CommonResult;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    public <T> CommonResult<T> getResult(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.setData(data);
        result.setSuccess(true);
        result.setCode(0);
        result.setMsg("성공했습니다.");
        return  result;
    }

    public <T> CommonResult<T> getSuccessResult() {
        CommonResult<T> result = new CommonResult<>();
        result.setSuccess(true);
        result.setCode(0);
        result.setMsg("성공했습니다.");
        return result;
    }

    public <T> CommonResult<T> getFailResult(int code, String msg) {
        CommonResult<T> result = new CommonResult<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
