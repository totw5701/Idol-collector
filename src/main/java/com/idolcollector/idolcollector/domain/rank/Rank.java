package com.idolcollector.idolcollector.domain.rank;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
@Getter
public class Rank {

    @Id @GeneratedValue
    @JoinColumn(name = "RANK_ID")
    private Long id;

    private String rank;
}
