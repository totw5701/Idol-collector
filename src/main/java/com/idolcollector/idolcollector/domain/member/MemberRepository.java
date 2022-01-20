package com.idolcollector.idolcollector.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNickName(String nickName);

    Optional<Member> findByEmail(String email);

    List<Member> findAll();

    @Query("select mt.member from MemberTag mt where mt.tag.name = :tagName")
    List<Member> findAllInTag(@Param("tagName") String tagName);

}