package com.idolcollector.idolcollector.domain.member;

import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.like.Likes;
import com.idolcollector.idolcollector.domain.membertag.MemberTag;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedComment;
import com.idolcollector.idolcollector.domain.notice.Notice;
import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.scrap.Scrap;
import com.idolcollector.idolcollector.web.dto.member.MemberUpdateRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue
    @JoinColumn(name = "MEMBER_ID")
    private Long id;
    private MemberRole role;
    private String nickName;
    private String email;
    private String pwd;
    private String name;
    private String picture;
    private LocalDateTime dateOfBirth;
    private LocalDateTime joinDate;
    private LocalDateTime modifyDate;


    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<NestedComment> nComments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberTag> memberTags = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Scrap> scraps = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Likes> likesList = new ArrayList<>();


    public Member(MemberRole role, String nickName, String email, String pwd, String name, String picture, LocalDateTime dateOfBirth) {
        this.role = role;
        this.nickName = nickName;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.picture = picture;
        this.dateOfBirth = dateOfBirth;

        this.joinDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();

    }


    // 비즈니스 로직
    public Long update(MemberUpdateRequestDto form) {
        this.nickName = form.getNickName();
        this.email = form.getEmail();
        this.pwd = form.getPwd();
        this.name = form.getName();
        this.picture = form.getPicture();

        return this.id;
    }

}
