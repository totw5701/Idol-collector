package com.idolcollector.idolcollector.domain.nestedcomment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NestedCommentRepository extends JpaRepository<NestedComment, Long> {

    @Query("select n from NestedComment n where n.comment.id = :commentId")
    List<NestedComment> findAllInComment(@Param("commentId") Long commentId);

}
