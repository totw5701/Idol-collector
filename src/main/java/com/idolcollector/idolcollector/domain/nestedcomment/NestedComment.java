package com.idolcollector.idolcollector.domain.nestedcomment;

import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.post.Post;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class NestedComment {

    @Id
    @GeneratedValue
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
}
