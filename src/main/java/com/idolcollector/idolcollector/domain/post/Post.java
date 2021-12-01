package com.idolcollector.idolcollector.domain.post;

import com.idolcollector.idolcollector.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    @JoinColumn(name = "POST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    private int views;
    private int likes;

    // 사진
    private String storeFileName;
    private String oriFileName;

    public Post(Member member, String title, String content, String storeFileName, String oriFileName) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.createDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();
        this.views = 0;
        this.likes = 0;
        this.storeFileName = storeFileName;
        this.oriFileName = oriFileName;

    }


    // 비즈니스 로직.
    public int addLike() {
        return ++this.likes;
    }

    public int addView() {
        return ++this.views;
    }


}
