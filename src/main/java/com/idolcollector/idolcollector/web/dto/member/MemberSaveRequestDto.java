package com.idolcollector.idolcollector.web.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveRequestDto {

    @NotBlank
    private String nickName;

    @NotBlank
    private String email;

    @NotBlank
    private String pwd;

    @NotBlank
    private String name;

    @Nullable
    private String picture;

    @NotBlank
    private LocalDateTime dateOfBirth;

}
