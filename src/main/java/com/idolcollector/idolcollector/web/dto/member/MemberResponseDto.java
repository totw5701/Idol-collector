package com.idolcollector.idolcollector.web.dto.member;

import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.notice.Notice;
import com.idolcollector.idolcollector.web.dto.notice.NoticeResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private String role;

    private String nickName;
    private String email;
    private String name;
    private String picture;
    private LocalDateTime dateOfBirth;
    private LocalDateTime joinDate;
    private LocalDateTime modifyDate;

    private List<NoticeResponseDto> notices = new ArrayList<>();

    public MemberResponseDto(Member member) {
        this.role = member.getRanks().getRole();
        this.nickName = member.getNickName();
        this.email = member.getEmail();
        this.name = member.getName();
        this.picture = member.getPicture();
        this.dateOfBirth = member.getDateOfBirth();
        this.joinDate = member.getJoinDate();
        this.modifyDate = member.getModifyDate();

        if (!member.getNotices().isEmpty()) {
            List<Notice> notices = member.getNotices();
            for (Notice notice : notices) {
                this.notices.add(new NoticeResponseDto(notice));
            }
        }
    }
}
