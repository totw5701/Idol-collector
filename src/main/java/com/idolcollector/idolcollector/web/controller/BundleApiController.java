package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.service.BundleService;
import com.idolcollector.idolcollector.web.dto.bundle.BundleAddCardDto;
import com.idolcollector.idolcollector.web.dto.bundle.BundleDeleteCardDto;
import com.idolcollector.idolcollector.web.dto.bundle.BundleResponseDto;
import com.idolcollector.idolcollector.web.dto.bundle.BundleSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/api/bundle")
@RequiredArgsConstructor
public class BundleApiController {

    private final BundleService bundleService;

    @ResponseBody
    @GetMapping("/{id}")
    public BundleResponseDto detail(@PathVariable("id") Long id) {
        BundleResponseDto byId = bundleService.findById(id);
        return byId;
    }

    @PostMapping("/create")
    public String create(BundleSaveDto form) {

        bundleService.save(form);
        return "redirect:/member";
    }

    @ResponseBody
    @PostMapping("/add-card")
    public Long addCard(BundleAddCardDto form) {
        return bundleService.addPost(form);
    }

    @ResponseBody
    @PostMapping("/delete-card")
    public Long addCard(BundleDeleteCardDto form) {
        return bundleService.deletePost(form);
    }
}
