package com.idolcollector.idolcollector.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResult {

    private int errorCode;
    private String message;
}
