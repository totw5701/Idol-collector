package com.idolcollector.idolcollector.web.dto.member;

import com.idolcollector.idolcollector.domain.rank.Ranks;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveRequestDto {

    private String nickName;
    private String email;
    private String pwd;
    private String name;

    @Nullable
    private String picture;
    private LocalDateTime dateOfBirth;

}
