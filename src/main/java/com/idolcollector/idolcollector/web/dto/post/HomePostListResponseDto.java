package com.idolcollector.idolcollector.web.dto.post;

import com.idolcollector.idolcollector.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class HomePostListResponseDto {

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

    public HomePostListResponseDto(Post post) {
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

}
