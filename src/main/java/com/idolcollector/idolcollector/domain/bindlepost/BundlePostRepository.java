package com.idolcollector.idolcollector.domain.bindlepost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BundlePostRepository extends JpaRepository<BundlePost, Long> {

    @Query("select bp from BundlePost bp where bp.post.id = :postId and bp.bundle.id = :bundleId")
    Optional<BundlePost> findByPostBundleId(@Param("postId") Long postId, @Param("bundleId") Long bundleId);
}
