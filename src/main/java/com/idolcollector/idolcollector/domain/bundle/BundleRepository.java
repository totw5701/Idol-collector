package com.idolcollector.idolcollector.domain.bundle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BundleRepository extends JpaRepository<Bundle, Long> {

    List<Bundle> findByMemberId(Long memberId);
}
