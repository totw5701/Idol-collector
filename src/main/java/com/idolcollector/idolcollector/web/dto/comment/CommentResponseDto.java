package com.idolcollector.idolcollector.web.dto.comment;

import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedComment;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentResponseDto {

    private Long id;

    private String authorNickName;
    private Long authorId;
    private String authorPicture;
    private String content;
    private int likes;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    List<NestedCommentResponseDto> nestedComments = new ArrayList<>();

    private boolean didLike = false;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.authorId = comment.getMember().getId();
        this.authorNickName = comment.getMember().getNickName();
        this.content = comment.getContent();
        this.likes = comment.getLikes();
        this.createDate = comment.getCreateDate();
        this.modifyDate = comment.getModifyDate();
        this.authorPicture = comment.getMember().getPicture();
    }

    public void setNCommentsDto(List<NestedCommentResponseDto> nestedComments) {
        this.nestedComments = nestedComments;
    }

    public void didLike() {
        this.didLike = true;
    }

}
