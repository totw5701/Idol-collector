package com.idolcollector.idolcollector.domain.tag;

import com.idolcollector.idolcollector.domain.member.Member;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Tag {

    @Id
    @GeneratedValue
    @JoinColumn(name = "TAG_ID")
    private Long id;

    private String name;
}
