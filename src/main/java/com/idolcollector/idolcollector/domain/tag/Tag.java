package com.idolcollector.idolcollector.domain.tag;

import com.idolcollector.idolcollector.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue
    @JoinColumn(name = "TAG_ID")
    private Long id;

    private String name;

    public Tag(String name) {
        this.name = name;
    }
}
