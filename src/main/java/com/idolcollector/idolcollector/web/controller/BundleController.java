package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.service.BundleService;
import com.idolcollector.idolcollector.service.ResponseService;
import com.idolcollector.idolcollector.web.dto.bundle.BundleAddCardDto;
import com.idolcollector.idolcollector.web.dto.bundle.BundleDeleteCardDto;
import com.idolcollector.idolcollector.web.dto.bundle.BundleResponseDto;
import com.idolcollector.idolcollector.web.dto.bundle.BundleSaveDto;
import com.idolcollector.idolcollector.web.dto.response.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/api/bundle")
@RequiredArgsConstructor
public class BundleController {

    private final BundleService bundleService;

    private final ResponseService responseService;

    @ResponseBody
    @GetMapping("/{id}")
    public CommonResult detail(@PathVariable("id") Long id) {

        BundleResponseDto result = bundleService.findById(id);

        return responseService.getResult(result);
    }

    @ResponseBody
    @PostMapping("/create")
    public CommonResult create(@Validated @RequestBody BundleSaveDto form) {

        Long id = bundleService.save(form);
        return responseService.getResult(id);
    }

    @ResponseBody
    @PostMapping("/add-card")
    public CommonResult addCard(@Validated @RequestBody BundleAddCardDto form) {

        bundleService.addPost(form);
        return responseService.getSuccessResult();
    }

    @ResponseBody
    @PostMapping("/delete-card")
    public CommonResult addCard(@Validated @RequestBody BundleDeleteCardDto form) {

        bundleService.deletePost(form);
        return responseService.getSuccessResult();
    }
}
