package com.idolcollector.idolcollector.web.dto.post;

import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSaveRequestDto {

    @NotNull
    private String title;
    private String content;

    // 사진
    @NotNull
    private MultipartFile attachFile;

    @Nullable
    private List<String> tags;

}
