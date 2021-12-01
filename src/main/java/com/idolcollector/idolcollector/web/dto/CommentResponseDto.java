package com.idolcollector.idolcollector.web.dto;

import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedComment;
import com.idolcollector.idolcollector.domain.post.Post;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentResponseDto {

    private String authorNickName;
    private Long authorId;
    private String content;
    private int likes;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    //List<NestedComment> nestedComments;

    public CommentResponseDto(Comment comment) {
        this.authorId = comment.getMember().getId();
        this.authorNickName = comment.getMember().getNickName();
        this.content = comment.getContent();
        this.likes = comment.getLikes();
        this.createDate = comment.getCreateDate();
        this.modifyDate = comment.getModifyDate();
    }
}
