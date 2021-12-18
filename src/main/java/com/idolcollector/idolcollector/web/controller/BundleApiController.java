package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.service.BundleService;
import com.idolcollector.idolcollector.web.dto.bundle.BundleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/bundle")
@RequiredArgsConstructor
public class BundleApiController {

    private final BundleService bundleService;

    @GetMapping("/{id}")
    public BundleResponseDto detail(@PathVariable("id") Long id) {
        BundleResponseDto byId = bundleService.findById(id);
        return byId;
    }
}
