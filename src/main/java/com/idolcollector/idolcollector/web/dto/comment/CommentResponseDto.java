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
    private String content;
    private int likes;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    List<NestedCommentResponseDto> nestedComments;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.authorId = comment.getMember().getId();
        this.authorNickName = comment.getMember().getNickName();
        this.content = comment.getContent();
        this.likes = comment.getLikes();
        this.createDate = comment.getCreateDate();
        this.modifyDate = comment.getModifyDate();

        this.nestedComments = new ArrayList<>();
        if (comment.getNComment() != null) {
            List<NestedComment> nComment = comment.getNComment();
            for (NestedComment nestedComment : nComment) {
                this.nestedComments.add(new NestedCommentResponseDto(nestedComment));
            }
        }
    }
}
