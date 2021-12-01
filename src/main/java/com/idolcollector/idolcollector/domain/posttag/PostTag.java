package com.idolcollector.idolcollector.domain.posttag;

import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.tag.Tag;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
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
}
