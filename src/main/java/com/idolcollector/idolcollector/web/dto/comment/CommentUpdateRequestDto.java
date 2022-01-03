package com.idolcollector.idolcollector.web.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateRequestDto {

    @NotNull
    private Long id;

    @NotBlank
    private String content;

}
