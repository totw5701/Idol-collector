package com.idolcollector.idolcollector.web.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveRequestDto {

    @NotNull
    private Long memberId;   // 추후에 세션에서 받아오는 걸로 수정

    @NotBlank
    private String title;
    @Nullable
    private String content;

    // 사진
    private MultipartFile attachFile;

    @Nullable
    private List<String> tags;

}
