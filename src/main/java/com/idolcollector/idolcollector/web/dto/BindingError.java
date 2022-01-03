package com.idolcollector.idolcollector.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BindingError {

    private String objectName;
    private String field;
    private String code;
    private String message;
}
