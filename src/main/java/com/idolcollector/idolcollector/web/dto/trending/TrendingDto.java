package com.idolcollector.idolcollector.web.dto.trending;

import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.trending.TrendingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class TrendingDto {

    private Post post;

    private int score;

}
