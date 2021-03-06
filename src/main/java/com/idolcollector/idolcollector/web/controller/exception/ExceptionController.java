package com.idolcollector.idolcollector.web.controller.exception;

import com.idolcollector.idolcollector.advice.exception.CNotLoginedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/exception")
public class ExceptionController {

    @GetMapping("/access-denied")
    public void accessdeniedException() {
        throw new AccessDeniedException("");
    }

    @GetMapping("/login")
    public void notLoginedExcetion() {
        throw new CNotLoginedException();
    }
}
