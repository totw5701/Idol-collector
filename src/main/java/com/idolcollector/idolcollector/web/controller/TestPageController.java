package com.idolcollector.idolcollector.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestPageController {

    @GetMapping("/")
    public ModelAndView testRootPage() {
        return new ModelAndView("index");
    }

    @GetMapping("/user")
    public String testUser() {
        return "USER";
    }

    @GetMapping("/manager")
    public String testMangager() {
        return "MANAGER";
    }

    @GetMapping("/admin")
    public String testAdmin() {
        return "admin";
    }
}
