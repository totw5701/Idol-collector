package com.idolcollector.idolcollector.domain.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Query("select l from Likes l where l.member.id = :memberId and l.targetId = :targetId and l.type = :likeType")
    Optional<Likes> findLikeByMemberIdPostId(@Param("targetId") Long targetId, @Param("memberId") Long memberId, @Param("likeType") LikeType likeType);

}
