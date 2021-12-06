package com.idolcollector.idolcollector.domain.trending;

import com.idolcollector.idolcollector.domain.post.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
public class Trending {

    @Id @GeneratedValue
    @JoinColumn(name = "TRENDING_ID")
    private Long id;

    @ManyToOne
    private Post post;

    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    private TrendingType type;

    public Trending(Post post, TrendingType type) {
        this.post = post;
        this.type = type;
        this.createDate = LocalDateTime.now();
    }

}
