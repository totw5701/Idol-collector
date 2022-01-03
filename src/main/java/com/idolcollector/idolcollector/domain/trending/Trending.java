package com.idolcollector.idolcollector.domain.trending;

import com.idolcollector.idolcollector.domain.post.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
public class Trending {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "TRENDING_ID")
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private Post post;

    private LocalDateTime createDate;

//    @Enumerated(EnumType.STRING)
//    private TrendingType type;

    private int score;

    public Trending(Post post, TrendingType type) {
        this.post = post;
        this.createDate = LocalDateTime.now();

        if (type == TrendingType.VIEW) this.score = 5;
        else if (type == TrendingType.LIKE) this.score = 12;
        else if (type == TrendingType.COMMENT) this.score = 20;
        else if (type == TrendingType.SCRAP) this.score = 30;
        else if (type == TrendingType.CREATE) this.score = 10;
    }

}
