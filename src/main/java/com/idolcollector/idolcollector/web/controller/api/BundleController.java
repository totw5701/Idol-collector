package com.idolcollector.idolcollector.web.controller.api;

import com.idolcollector.idolcollector.service.BundleService;
import com.idolcollector.idolcollector.service.ResponseService;
import com.idolcollector.idolcollector.web.dto.bundle.*;
import com.idolcollector.idolcollector.web.dto.response.CommonResult;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"카드집"})
@Slf4j
@Controller
@RequestMapping("/api/bundles")
@RequiredArgsConstructor
public class BundleController {

    private final BundleService bundleService;

    private final ResponseService responseService;

    @ApiOperation(value = "카드집 정보", notes = "카드집 정보를 조회합니다.")
    @ResponseBody
    @GetMapping("/{id}")
    public CommonResult<BundleResponseDto> detail(@PathVariable("id") Long id) {

        BundleResponseDto result = bundleService.findById(id);

        return responseService.getResult(result);
    }

    @ApiOperation(value = "카드집 생성", notes = "카드집을 생성합니다.")
    @ResponseBody
    @PostMapping()
    public CommonResult<Long> create(@ApiParam @Validated @RequestBody BundleSaveDto form) {

        Long id = bundleService.save(form);
        return responseService.getResult(id);
    }

    @ApiOperation(value = "카드집 수정", notes = "카드집을 수정합니다.")
    @ResponseBody
    @PatchMapping()
    public CommonResult<Long> update(@ApiParam @Validated @RequestBody BundleUpdateDto form) {

        Long id = bundleService.update(form);
        return responseService.getResult(id);
    }

    @ApiOperation(value = "카드집 삭제", notes = "카드집을 삭제합니다.")
    @ResponseBody
    @DeleteMapping("/{id}")
    public CommonResult<Long> delete(@PathVariable("id") Long id) {

        bundleService.delete(id);
        return responseService.getResult(id);
    }

    @ApiOperation(value = "카드 추가", notes = "카드집에 카드를 추가합니다.")
    @ResponseBody
    @PostMapping("/card")
    public CommonResult<Object> addCard(@ApiParam @Validated @RequestBody BundleAddCardDto form) {

        bundleService.addPost(form);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "카드 삭제", notes = "카드집에서 카드를 삭제합니다.")
    @ResponseBody
    @DeleteMapping("/card")
    public CommonResult<Object> deteteCard(@ApiParam @Validated @RequestBody BundleDeleteCardDto form) {
        bundleService.deletePost(form);
        return responseService.getSuccessResult();
    }
}
