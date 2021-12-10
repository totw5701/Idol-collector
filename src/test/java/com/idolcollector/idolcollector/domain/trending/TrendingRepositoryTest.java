package com.idolcollector.idolcollector.domain.trending;

import com.idolcollector.idolcollector.domain.comment.CommentRepository;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedCommentRepository;
import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.post.PostRepository;
import com.idolcollector.idolcollector.domain.rank.Ranks;
import com.idolcollector.idolcollector.domain.rank.RanksRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class TrendingRepositoryTest {

    @Autowired
    TrendingRepository trendingRepository;

    @Autowired NestedCommentRepository nestedCommentRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired RanksRepository ranksRepository;
    @Autowired PostRepository postRepository;

    @BeforeEach
    void before() {
        Ranks rank = new Ranks("ROLE_USER");
        ranksRepository.save(rank);
        Member member = new Member(rank, "nick", "email", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());
        memberRepository.save(member);

        Post post = new Post(member, "title", "conten", "ste", "ori");
        postRepository.save(post);

        Post post2 = new Post(member, "title2", "conten2", "ste", "ori");
        postRepository.save(post2);
    }

    @Test
    void queryTest() {
        
        // Given
        List<Post> pAll = postRepository.findAll();
        Post p1 = pAll.get(0);
        Post p2 = pAll.get(1);

        Trending p1T1 = new Trending(p1, TrendingType.VIEW);
        Trending p1T2 = new Trending(p1, TrendingType.VIEW);
        Trending p1T3 = new Trending(p1, TrendingType.LIKE);
        Trending p1T4 = new Trending(p1, TrendingType.COMMENT);
        Trending p1T5 = new Trending(p1, TrendingType.COMMENT);
        Trending p1T6 = new Trending(p1, TrendingType.COMMENT);
        Trending p1T7 = new Trending(p1, TrendingType.VIEW);

        trendingRepository.save(p1T1);
        trendingRepository.save(p1T2);
        trendingRepository.save(p1T3);
        trendingRepository.save(p1T4);
        trendingRepository.save(p1T5);
        trendingRepository.save(p1T6);
        trendingRepository.save(p1T7);


        Trending p2T1 = new Trending(p2, TrendingType.VIEW);
        Trending p2T2 = new Trending(p2, TrendingType.VIEW);
        Trending p2T3 = new Trending(p2, TrendingType.VIEW);

        trendingRepository.save(p2T1);
        trendingRepository.save(p2T2);
        trendingRepository.save(p2T3);

        // When
        List<Post> list = trendingRepository.trendAnalyByDate(LocalDateTime.now().minusDays(7));

        Post first = list.get(0);
        Post second = list.get(1);

        // Then
        assertThat(first.getId()).isEqualTo(p1.getId());
        assertThat(second.getId()).isEqualTo(p2.getId());
    }



}