package com.idolcollector.idolcollector.domain.blame;

import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.post.Post;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Blame {

    @Id
    @GeneratedValue
    @JoinColumn(name = "BLAME_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    @Nullable
    private Post post;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    @Nullable
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "TARGET_MEMBER_ID")
    private Member targetMember;

    private String message;
    private LocalDateTime createDate;
}
