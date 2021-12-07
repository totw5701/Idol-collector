package com.idolcollector.idolcollector.web.dto.nestedcomment;

import com.idolcollector.idolcollector.domain.nestedcomment.NestedComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NestedCommentResponseDto {

    private Long id;

    private String authorNickName;
    private Long authorId;
    private String content;
    private int likes;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;


    public NestedCommentResponseDto(NestedComment comment) {
        this.id = comment.getId();
        this.authorId = comment.getMember().getId();
        this.authorNickName = comment.getMember().getNickName();
        this.content = comment.getContent();
        this.likes = comment.getLikes();
        this.createDate = comment.getCreateDate();
        this.modifyDate = comment.getModifyDate();
    }
}
