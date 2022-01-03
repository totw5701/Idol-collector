package com.idolcollector.idolcollector.domain.scrap;

import com.idolcollector.idolcollector.domain.like.LikeType;
import com.idolcollector.idolcollector.domain.like.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    @Query("select s from Scrap s where s.member.id = :memberId and s.post.id = :postId")
    Optional<Scrap> findScrapByMemberIdPostId(@Param("postId") Long targetId, @Param("memberId") Long memberId);

}
