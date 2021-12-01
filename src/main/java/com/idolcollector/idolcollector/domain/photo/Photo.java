package com.idolcollector.idolcollector.domain.photo;

import com.idolcollector.idolcollector.domain.post.Post;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Photo {

    @Id
    @GeneratedValue
    @JoinColumn(name = "PHOTO_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    private String storeFileName;
    private String oriFileName;
}
