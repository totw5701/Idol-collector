package com.idolcollector.idolcollector.web.controller.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SocialLoginController {

    private final HttpSession httpSession;

    @Value("${spring.url.base}")
    String baseUrl;

    @GetMapping("/login-success")
    public String loginSuccess() {
        return httpSession.getId();
    }

    @GetMapping("/login-url")
    public String loginUrl() {
        return baseUrl + "/oauth2/authorization/google";
    }
}
