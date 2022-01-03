package com.idolcollector.idolcollector.domain.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    @Query("select pt.tag from PostTag pt where pt.post.id = :postId")
    List<Tag> findAllByPostId(@Param("postId") Long postId);

    @Transactional
    @Modifying
    @Query("delete from PostTag pt where pt.post.id = :postId")
    void deleteAllByPostId(@Param("postId") Long postId);
}
