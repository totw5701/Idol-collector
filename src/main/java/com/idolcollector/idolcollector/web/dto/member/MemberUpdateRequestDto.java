package com.idolcollector.idolcollector.web.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequestDto {

    @NotNull
    private Long memberId;

    @NotBlank
    private String nickName;

    @NotBlank
    private String email;

    @NotBlank
    private String pwd;

    @NotBlank
    private String name;

    @NotBlank
    private String picture;

}
