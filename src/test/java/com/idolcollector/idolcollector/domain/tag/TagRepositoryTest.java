package com.idolcollector.idolcollector.domain.tag;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;


    @Test
    void 생성() {

        // Given
        Tag aAa = new Tag("aAa");
        Tag bbb = new Tag("bbb");
        Tag 이하늬 = new Tag("이하늬");

        // When
        tagRepository.save(aAa);
        tagRepository.save(bbb);
        tagRepository.save(이하늬);

        // Then

    }

}