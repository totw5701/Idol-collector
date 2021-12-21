package com.idolcollector.idolcollector.config.auth.dto;

import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@AllArgsConstructor
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;


    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return new OAuthAttributes(attributes,
                userNameAttributeName,
                (String) attributes.get("name"),
                (String) attributes.get("email"),
                (String) attributes.get("picture"));
    }


    public Member toEntity() {
        return new Member(MemberRole.USER, name, email, "", name, picture, LocalDateTime.now());
    }
}
