package com.idolcollector.idolcollector.web.dto.post;

import com.idolcollector.idolcollector.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostSaveRequestDto {

    private Long memberId;

    private String title;
    private String content;

    // 사진
    private String storeFileName;
    private String oriFileName;

    public PostSaveRequestDto(Long memberId, String title, String content, String storeFileName, String oriFileName) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.storeFileName = storeFileName;
        this.oriFileName = oriFileName;
    }
}
