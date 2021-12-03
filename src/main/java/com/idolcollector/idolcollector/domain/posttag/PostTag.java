package com.idolcollector.idolcollector.domain.posttag;

import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.tag.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PostTag {

    @Id
    @GeneratedValue
    @JoinColumn(name = "POST_TAG_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    public PostTag(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
    }

    // 연관관계 메소드
    private void construct(Post post) {
        post.getPostTags().add(this);
        // Tag와는 단방향 연관관계를 맺고있음.
    }
}
