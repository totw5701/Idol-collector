package com.idolcollector.idolcollector.domain.member;

import com.idolcollector.idolcollector.domain.membertag.MemberTag;
import com.idolcollector.idolcollector.domain.membertag.MemberTagRepository;
import com.idolcollector.idolcollector.domain.rank.Ranks;
import com.idolcollector.idolcollector.domain.rank.RanksRepository;
import com.idolcollector.idolcollector.domain.tag.Tag;
import com.idolcollector.idolcollector.domain.tag.TagRepository;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired RanksRepository ranksRepository;
    @Autowired TagRepository tagRepository;
    @Autowired MemberTagRepository memberTagRepository;

    @BeforeEach
    void before() {
        Ranks rank = new Ranks("ROLL_USER");
        ranksRepository.save(rank);

        Tag tag = new Tag("이하늬");
        tagRepository.save(tag);

    }

    @Test
    void 저장_조회() {

        //Given
        List<Ranks> all = ranksRepository.findAll();
        Ranks rank = all.get(0);

        Member member = new Member(rank, "nick", "email", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());

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
        List<Ranks> all = ranksRepository.findAll();
        Ranks rank = all.get(0);

        Member member = new Member(rank, "nick", "email", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());
        memberRepository.save(member);

        List<Tag> tags = tagRepository.findAll();
        Tag tag = tags.get(0);

        MemberTag memberTag = new MemberTag(member, tag);
        memberTagRepository.save(memberTag);

        //When
        List<Member> members = memberRepository.findAllInTag("이하늬");

        //Then
        assertThat(members.size()).isEqualTo(1);
    }

}