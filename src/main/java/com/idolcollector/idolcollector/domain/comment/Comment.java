package com.idolcollector.idolcollector.domain.comment;

import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue
    @JoinColumn(name = "COMMENT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    private String content;
    private int likes;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    public Comment(Member member, Post post, String content) {
        this.member = member;
        this.post = post;
        this.content = content;
        this.likes = 0;
        this.createDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();
    }

    // 비즈니스 로직
    public Long update(String content) {
        this.content = content;
        this.modifyDate = LocalDateTime.now();
        return this.id;
    }


    public int addLike() {
        return ++this.likes;
    }


}
