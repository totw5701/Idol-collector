package com.idolcollector.idolcollector.domain.trending;

import com.idolcollector.idolcollector.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TrendingRepository extends JpaRepository<Trending, Long> {


    @Query("select t.post from Trending t where t.createDate > :period group by t.post order by sum(t.score) desc")
    List<Post> trendAnalyByDate(@Param("period")LocalDateTime period);


    /**
     *
     * 아래 코드는 Trending의 type을 Enum String으로 저장했을 때 쓰려던 쿼리입니다.
     * JPQL은 inkine-view(subquery를 formwjf에 사용하는 기술)를 제공하지 않습니다.
     * 따라서 기능을 구현하려면 native쿼리를 사용해야 하는데 너무 복잡해서 나중에 수정하는걸로!.
     *
     */

    /*
    @Query("select t.post, count(t.type) from Trending t where t.createDate > :period group by t.post")
    List<Object[]> trendAnalyByDate(@Param("period")LocalDateTime period);

    @Query("select t.post, t.type as type from Trending t where t.createDate > :period group by t.post, t.type")
    List<Object[]> asdasd(@Param("period")LocalDateTime period);

    @Query("select t.post, t.type as type, count(t.type) as count from Trending t where t.createDate > :period group by t.post, t.type")
    List<Object[]> asasdasd(@Param("period")LocalDateTime period);

    @Query("select t.post, t.type as type, case when (t.type = 'VIEW') then count(t.type) * 10 case when (t.type = 'LIKE') then count(t.type) * 10 case when (t.type = 'COMMENT') then count(t.type) * 10 case when (t.type = 'SCRAP') then count(t.type) * 10 as score from Trending t where t.createDate > :period group by t.post, t.type")
    List<Object[]> asasdasd(@Param("period")LocalDateTime period);
    */
}

