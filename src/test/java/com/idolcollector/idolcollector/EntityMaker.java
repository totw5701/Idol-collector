package com.idolcollector.idolcollector;

import com.idolcollector.idolcollector.domain.bundle.Bundle;
import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.like.Likes;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRole;
import com.idolcollector.idolcollector.domain.notice.Notice;
import com.idolcollector.idolcollector.domain.notice.NoticeType;
import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.posttag.PostTag;
import com.idolcollector.idolcollector.domain.tag.Tag;
import com.idolcollector.idolcollector.web.dto.file.UploadFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EntityMaker {

    public static Member generateMember() {
        return new Member(MemberRole.USER, "testMember", "testEmail@email.net", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());
    }

    public static Post generatePost(Member member) {
        Post post = new Post(member, "test card", "test card content", "ste", "ori");

        post.getComments().add(new Comment(generateMember(), post, "comment1"));
        post.getComments().add(new Comment(generateMember(), post, "comment2"));

        post.getPostTags().add(new PostTag(post, new Tag("tag1")));
        post.getPostTags().add(new PostTag(post, new Tag("tag2")));

        return post;
    }

    public static Comment generateComment(Post post, Member member) {
        return new Comment(member, post, "comment content");
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

    public static UploadFile generateUploadFile() {
        return new UploadFile("uploaded file name", "store file name");
    }

//    public static Likes generateLike() {
//        return new Likes()
//    }
}
