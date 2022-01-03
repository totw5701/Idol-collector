package com.idolcollector.idolcollector.web.dto.bundle;

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

    @NotBlank
    private String title;
    private String description;
}
