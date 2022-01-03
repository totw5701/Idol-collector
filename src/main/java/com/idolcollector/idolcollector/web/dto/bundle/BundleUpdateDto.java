package com.idolcollector.idolcollector.web.dto.bundle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BundleUpdateDto {

    @NotNull
    private Long bundleId;

    @NotBlank
    private String title;
    private String description;
}
