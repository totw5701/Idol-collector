package com.idolcollector.idolcollector.config.auth;

import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**").permitAll()
                    .antMatchers("/manager").hasAnyRole(MemberRole.MANAGER.name(), MemberRole.ADMIN.name())
                    .antMatchers("/admin").hasRole(MemberRole.ADMIN.name())
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
    }
}

