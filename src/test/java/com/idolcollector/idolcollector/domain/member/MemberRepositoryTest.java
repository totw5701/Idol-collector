package com.idolcollector.idolcollector.domain.member;

import com.idolcollector.idolcollector.domain.membertag.MemberTag;
import com.idolcollector.idolcollector.domain.membertag.MemberTagRepository;
import com.idolcollector.idolcollector.domain.tag.Tag;
import com.idolcollector.idolcollector.domain.tag.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TagRepository tagRepository;
    @Autowired MemberTagRepository memberTagRepository;

    @BeforeEach
    void before() {
        Tag tag = new Tag("qqqqeeeererwr#@#wr13");
        tagRepository.save(tag);

    }

    @Test
    void 저장_조회() {

        //Given
        Member member = new Member(MemberRole.USER, "nick", "email", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());

        //When
        Member save = memberRepository.save(member);
        memberRepository.flush();
        Member find = memberRepository.findById(save.getId()).get();

        //Then
        assertThat(find.getNickName()).isEqualTo("nick");
    }


    @Test
    void 태그로_회원_모두_가져오기() {

        //Given
        Member member = new Member(MemberRole.USER, "nick", "email", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());
        memberRepository.save(member);

        Tag tag = tagRepository.findByName("qqqqeeeererwr#@#wr13").get();

        MemberTag memberTag = new MemberTag(member, tag);
        memberTagRepository.save(memberTag);

        //When
        List<Member> members = memberRepository.findAllInTag("qqqqeeeererwr#@#wr13");

        //Then
        assertThat(members.size()).isEqualTo(1);
    }

}