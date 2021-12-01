package com.idolcollector.idolcollector.domain.rank;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class RanksRepositoryTest {

    @Autowired
    RanksRepository ranksRepository;

    @Test
    void 저장_조회() {

        // Given
        Ranks ranks = new Ranks("ROLL_USER");

        // When
        Ranks saved = ranksRepository.save(ranks);
        ranksRepository.flush();
        Ranks find = ranksRepository.findById(saved.getId()).get();

        // Then
        assertThat(find.getRoll()).isEqualTo(ranks.getRoll());
    }

}