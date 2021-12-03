package com.idolcollector.idolcollector.web.dto.tag;

import com.idolcollector.idolcollector.domain.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TagResponseDto {

    private String name;

    public TagResponseDto(Tag tag) {
        this.name = tag.getName();
    }

}
