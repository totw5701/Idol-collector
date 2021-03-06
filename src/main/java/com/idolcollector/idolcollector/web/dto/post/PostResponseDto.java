package com.idolcollector.idolcollector.web.dto.post;

import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.posttag.PostTag;
import com.idolcollector.idolcollector.web.dto.comment.CommentResponseDto;
import com.idolcollector.idolcollector.web.dto.tag.TagResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostResponseDto {

    private Long id;

    private String authorNickName;
    private Long authorId;

    private String title;
    private String content;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    private int views;
    private int likes;

    private String storeFileName;
    private String oriFileName;

    List<CommentResponseDto> comments = new ArrayList<>();
    List<TagResponseDto> tags = new ArrayList<>();

    private boolean didLike = false;
    private boolean didScrap = false;


    public PostResponseDto(Post post) {

        this.id = post.getId();
        this.authorId = post.getMember().getId();
        this.authorNickName = post.getMember().getNickName();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createDate = post.getCreateDate();
        this.modifyDate = post.getModifyDate();
        this.views = post.getViews();
        this.likes = post.getLikes();
        this.storeFileName = post.getStoreFileName();
        this.oriFileName = post.getOriFileName();
    }

    public void setCommentsDto(List<CommentResponseDto> list) {
        this.comments = list;
    }

    public void setTagsDto(List<TagResponseDto> list) {
        this.tags = list;
    }

    public void didLike() {
        this.didLike = true;
    }

    public void didScrap() {
        this.didScrap = true;
    }


}
