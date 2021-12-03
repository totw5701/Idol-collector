package com.idolcollector.idolcollector.domain.notice;

import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.tag.Tag;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Notice {

    @Id
    @GeneratedValue
    @JoinColumn(name = "NOTICE_TAG_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "TARGET_MEMBER_ID")
    private Member targetMember;   // 이 알림을 유발한 사용자.

    @ManyToOne
    @JoinColumn(name = "TARGET_POST_ID")
    private Post targetPost;

    @ManyToOne
    @JoinColumn(name = "TARGET_COMMENT_ID")
    private Comment targetComment;

    private String noticeType;
    private String message;
    private LocalDateTime createDate;
    private String url; // 클릭하면 이동할 url

    // 생성자

    // 연관관계 메서드.

}
