package com.idolcollector.idolcollector.domain.post;

import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedComment;
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
}
