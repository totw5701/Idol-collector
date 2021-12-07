package com.idolcollector.idolcollector.web.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveRequestDto {

    @NotNull
    private Long memberId;

    @NotBlank
    private String title;
    @Nullable
    private String content;

    // 사진
    @Nullable
    private String storeFileName;
    @Nullable
    private String oriFileName;

    @Nullable
    private List<String> tags;

}
