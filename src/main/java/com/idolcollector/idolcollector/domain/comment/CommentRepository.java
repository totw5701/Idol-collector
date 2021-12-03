package com.idolcollector.idolcollector.domain.comment;

import com.idolcollector.idolcollector.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

//    @Query("select c from Comment c where c.post.id = :postId")
//    List<Comment> findAllInPost(@Param("postId") Long postId);

    List<Comment> findAllByPostId(Long postId);

    List<Comment> findAllByMemberId(Long memberId);

}
