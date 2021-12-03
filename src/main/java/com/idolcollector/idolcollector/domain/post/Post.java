package com.idolcollector.idolcollector.domain.post;

import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.posttag.PostTag;
import com.idolcollector.idolcollector.domain.scrap.Scrap;
import com.idolcollector.idolcollector.web.dto.post.PostUpdateRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<PostTag> postTags = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Scrap> scraps = new ArrayList<>();

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

        this.construct(member);
    }

    // 연관관계 메서드
    public void construct(Member member) {
        member.getPosts().add(this);
    }

    // 비즈니스 로직.
    public Long update(PostUpdateRequestDto form) {

        this.title = form.getTitle();
        this.content = form.getContent();
        this.modifyDate = LocalDateTime.now();

        return this.id;
    }

    public int addLike() {
        return ++this.likes;
    }

    public int addView() {
        return ++this.views;
    }


}
