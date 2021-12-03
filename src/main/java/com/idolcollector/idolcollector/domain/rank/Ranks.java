package com.idolcollector.idolcollector.domain.rank;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
@Getter
@NoArgsConstructor
public class Ranks {

    @Id @GeneratedValue
    @JoinColumn(name = "RANKS_ID")
    private Long id;

    private String roll;

    public Ranks(String roll) {
        this.roll = roll;
    }
}
