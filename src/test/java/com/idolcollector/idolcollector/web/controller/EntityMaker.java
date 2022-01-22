package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.domain.bundle.Bundle;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRole;
import com.idolcollector.idolcollector.domain.notice.Notice;
import com.idolcollector.idolcollector.domain.notice.NoticeType;
import com.idolcollector.idolcollector.domain.post.Post;

import java.time.LocalDateTime;

public class EntityMaker {

    public static Member generateMember() {
        return new Member(MemberRole.USER, "testMember", "testEmail@email.net", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());
    }

    public static Post generatePost() {
        Member member = new Member(MemberRole.USER, "testMember", "testEmail@email.net", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());
        Post post = new Post(member, "test card", "test card content", "ste", "ori");
        return post;
    }

    public static Notice generateNotice() {
        Member member = new Member(MemberRole.USER, "testMember", "testEmail@email.net", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());
        Member member2 = new Member(MemberRole.USER, "testMember2", "testEmail2@email.net", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());
        Post post = new Post(member, "test card", "test card content", "ste", "ori");
        return new Notice(member, member2, post, NoticeType.LIKE);
    }

    public static Bundle generateBundle() {
        Member member = new Member(MemberRole.USER, "testMember", "testEmail@email.net", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());
        return new Bundle(member, "test bundle", "bundle description");
    }
}
