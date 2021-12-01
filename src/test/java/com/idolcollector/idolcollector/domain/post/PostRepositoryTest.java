package com.idolcollector.idolcollector.domain.post;

import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.posttag.PostTag;
import com.idolcollector.idolcollector.domain.posttag.PostTagRepository;
import com.idolcollector.idolcollector.domain.rank.Ranks;
import com.idolcollector.idolcollector.domain.rank.RanksRepository;
import com.idolcollector.idolcollector.domain.scrap.Scrap;
import com.idolcollector.idolcollector.domain.scrap.ScrapRepository;
import com.idolcollector.idolcollector.domain.tag.Tag;
import com.idolcollector.idolcollector.domain.tag.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@SpringBootTest
class PostRepositoryTest {

    @Autowired PostRepository postRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired RanksRepository ranksRepository;
    @Autowired TagRepository tagRepository;
    @Autowired PostTagRepository postTagRepository;
    @Autowired ScrapRepository scrapRepository;

    @BeforeEach
    void before() {
        Ranks rank = new Ranks("ROLL_USER");
        ranksRepository.save(rank);
        Member member = new Member(rank, "nick", "email", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());
        memberRepository.save(member);
        Tag tag = new Tag("이하늬");
        tagRepository.save(tag);
    }

    @Test
    void 저장_조회() {
        //Given
        List<Member> members = memberRepository.findAll();
        Member member = members.get(0);

        Post post = new Post(member, "title", "content", "storeFilename", "oriFileName");

        //When
        Post save = postRepository.save(post);
        Post find = postRepository.findById(save.getId()).get();
        postRepository.flush();

        //Then
        assertThat(find.getId()).isEqualTo(post.getId());
        assertThat(find.getTitle()).isEqualTo("title");
    }

    @Test
    void 삭제() {
        //Given
        Member member = memberRepository.findAll().get(0);

        Post post = new Post(member, "title", "content", "storeFilename", "oriFileName");
        Post save = postRepository.save(post);
        postRepository.flush();

        //When
        postRepository.delete(save);
        Optional<Post> result = postRepository.findById(save.getId());

        //Then
        assertThat(result.isPresent()).isEqualTo(false);
    }

    @Test
    void 회원_게시글_모두조회() {
        //Given
        Member member = memberRepository.findAll().get(0);

        Post post1 = new Post(member, "title1", "content1", "storeFilename1", "oriFileName1");
        Post post2 = new Post(member, "title2", "content2", "storeFilename2", "oriFileName2");

        postRepository.save(post1);
        postRepository.save(post2);

        //When
        List<Post> posts = postRepository.findAllInMember(member.getId());

        //Then
        assertThat(posts.size()).isEqualTo(2);
    }

    @Test
    void 태그로_모든_게시글_조회() {
        //Given
        Member member = memberRepository.findAll().get(0);

        Post post = new Post(member, "title1", "content1", "storeFilename1", "oriFileName1");
        postRepository.save(post);

        List<Tag> tags = tagRepository.findAll();
        Tag tag = tags.get(0);

        PostTag postTag = new PostTag(post, tag);
        postTagRepository.save(postTag);

        //When
        List<Post> posts = postRepository.findAllInTag(tag.getName());

        //Then
        assertThat(posts.size()).isEqualTo(1);
    }

    @Test
    void 회원이_스크랩한_모든_게시글_조회() {
        //Given
        Member member = memberRepository.findAll().get(0);

        Post post = new Post(member, "title1", "content1", "storeFilename1", "oriFileName1");
        postRepository.save(post);

        Scrap scrap = new Scrap(member, post);
        scrapRepository.save(scrap);

        //When
        List<Post> posts = postRepository.findAllInScrap(member.getId());

        //Then
        assertThat(posts.size()).isEqualTo(1);
    }

}