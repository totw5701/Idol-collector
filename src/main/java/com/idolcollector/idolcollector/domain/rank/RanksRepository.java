package com.idolcollector.idolcollector.domain.rank;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RanksRepository extends JpaRepository<Ranks, Long> {

    Ranks findByRoll(String roll);

}
