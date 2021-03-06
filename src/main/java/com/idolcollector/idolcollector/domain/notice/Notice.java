package com.idolcollector.idolcollector.domain.notice;

import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedComment;
import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.tag.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "NOTICE_TAG_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "TARGET_MEMBER_ID")
    private Member targetMember;   // 이 알림을 유발한 사용자.

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "TARGET_POST_ID")
    private Post targetPost;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "TARGET_COMMENT_ID")
    private Comment targetComment;

    @Enumerated(EnumType.STRING)
    private NoticeType noticeType;
    private LocalDateTime createDate;

    // 생성자
    public Notice(Member member, Member targetMember, Post targetPost, NoticeType type) {

        this.member = member;
        this.targetMember = targetMember;
        this.targetPost = targetPost;
        this.noticeType = type;
        this.createDate = LocalDateTime.now();

        construct(member);
    }

    public Notice(Member member, Member targetMember, Post targetPost, Comment targetComment, NoticeType type) {

        this.member = member;
        this.targetMember = targetMember;
        this.targetPost = targetPost;
        this.targetComment = targetComment;
        this.noticeType = type;
        this.createDate = LocalDateTime.now();

        construct(member);
    }

    // 연관관계 메서드.
    private void construct(Member member) {
        member.getNotices().add(this);
    }
}
