package com.idolcollector.idolcollector.domain.nestedcomment;

import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class NestedComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "COMMENT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    private String content;
    private int likes;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;


    public NestedComment(Member member, Comment comment, String content) {
        this.member = member;
        this.comment = comment;
        this.content = content;
        this.likes = 0;
        this.createDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();
        this.construct(member, comment);
    }

    // 연관관계 메서드
    private void construct(Member member, Comment comment) {
        comment.getNComment().add(this);
        comment.getNComment().add(this);
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
