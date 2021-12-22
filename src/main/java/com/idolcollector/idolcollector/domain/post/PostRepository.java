package com.idolcollector.idolcollector.domain.post;

import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.member.id = :memberId")
    List<Post> findAllInMember(@Param("memberId") Long memberId);

    @Query("select pt.post from PostTag pt join pt.tag t where t.name = :tagName")
    List<Post> findAllInTag(@Param("tagName") String tagName);

    @Query("select s.post from Scrap s where s.member.id = :memberId")
    List<Post> findAllInScrap(@Param("memberId") Long memberId);


    @Query("select p from Post p order by p.id desc")
    Page<Post> findAll(Pageable pageable);


    @Query(value = "(SELECT post.id, post.content, post.create_date, post.likes, post.modify_date, post.ori_file_name, post.store_file_name, post.title, post.views, post.member_id FROM trending LEFT JOIN post ON trending.post_id = post.id GROUP BY post_id ORDER BY SUM(score) desc limit 1000000) UNION (SELECT * FROM post ORDER BY post.id desc limit 1000000)", nativeQuery = true)
    List<Post> findTrendingAll(Pageable pageable);

    /**
     *
     * 아래 코드는 사용하지 않는 코드입니다.
     *
     */
    @Query("select p, c from Comment c, Post p where p.id = :postId")
    List<Object[]> multipleQ(@Param("postId") Long postId);

    @Query("select p, c, nc from Comment c, Post p left outer join NestedComment nc on nc.comment.id = c.id where p.id = :postId")
    List<Object[]> multipleQ2(@Param("postId") Long postId);
}


