package com.idolcollector.idolcollector.web.dto.notice;

import java.time.LocalDateTime;

public class NoticeResponseDto {

    private String member;
    private String targetMember;

    private String targetPost;

    private String targetComment;

    private String type;
    private LocalDateTime createDate;
}
