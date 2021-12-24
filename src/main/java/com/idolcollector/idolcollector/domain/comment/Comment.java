package com.idolcollector.idolcollector.domain.comment;

import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedComment;
import com.idolcollector.idolcollector.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "COMMENT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "POST_ID")
    private Post post;

    private String content;
    private int likes;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "comment", orphanRemoval = true)
    private List<NestedComment> nComment = new ArrayList<>();

    public Comment(Member member, Post post, String content) {
        this.member = member;
        this.post = post;
        this.content = content;
        this.likes = 0;
        this.createDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();

        this.construct(member, post);
    }

    // 연관관계 메소드
    private void construct(Member member, Post post) {
        member.getComments().add(this);
        post.getComments().add(this);
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
