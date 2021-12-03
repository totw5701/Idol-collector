package com.idolcollector.idolcollector.web.dto.post;

import com.idolcollector.idolcollector.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
