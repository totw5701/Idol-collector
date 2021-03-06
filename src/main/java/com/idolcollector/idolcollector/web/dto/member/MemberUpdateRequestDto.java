package com.idolcollector.idolcollector.web.dto.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequestDto {

    @ApiModelProperty(value = "닉네임", example = "이하늬덕후", required = true)
    @NotBlank
    private String nickName;

    @NotBlank
    private String email;

    @ApiModelProperty(value = "프로필이미지", required = true)
    @NotNull
    private MultipartFile profile;

}
