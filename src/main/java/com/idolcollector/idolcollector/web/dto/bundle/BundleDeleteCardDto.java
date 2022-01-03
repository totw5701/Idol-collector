package com.idolcollector.idolcollector.web.dto.bundle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BundleDeleteCardDto {

    private Long postId;
    private Long bundleId;

}
