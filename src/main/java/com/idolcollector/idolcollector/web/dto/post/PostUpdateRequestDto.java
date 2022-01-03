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
public class PostUpdateRequestDto {

    @NotNull
    private Long postId;

    @NotBlank
    private String title;
    private String content;

    @Nullable
    private List<String> tags;
}
