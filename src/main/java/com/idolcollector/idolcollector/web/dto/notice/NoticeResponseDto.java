package com.idolcollector.idolcollector.web.dto.notice;

import com.idolcollector.idolcollector.domain.notice.Notice;

import java.time.LocalDateTime;

public class NoticeResponseDto {

    private String memberNickName;
    private String targetMemberNickName; // 이 알림을 유발한 사용자.
    private String targetPostTitle;
    private String targetComment;
    private String type;
    private LocalDateTime createDate;

    public NoticeResponseDto(Notice notice) {

        this.memberNickName = notice.getMember().getNickName();
        this.targetMemberNickName = notice.getTargetMember().getNickName();
        this.targetPostTitle = notice.getTargetPost().getTitle();

        if (notice.getTargetComment() != null) {
            this.targetComment = notice.getTargetComment().getContent();
        }

        this.type = notice.getNoticeType().toString();
        this.createDate = notice.getCreateDate();
    }
}
