package com.idolcollector.idolcollector.web.controller.api;

import com.idolcollector.idolcollector.service.BundleService;
import com.idolcollector.idolcollector.service.ResponseService;
import com.idolcollector.idolcollector.web.dto.bundle.BundleAddCardDto;
import com.idolcollector.idolcollector.web.dto.bundle.BundleDeleteCardDto;
import com.idolcollector.idolcollector.web.dto.bundle.BundleResponseDto;
import com.idolcollector.idolcollector.web.dto.bundle.BundleSaveDto;
import com.idolcollector.idolcollector.web.dto.pageresponsedto.CardDetailPageDto;
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
@RequestMapping("/api/bundle")
@RequiredArgsConstructor
public class BundleController {

    private final BundleService bundleService;

    private final ResponseService responseService;

    @ApiOperation(value = "카드집 정보", notes = "카드집 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    code = 200
                    , response = BundleDetailClass.class
                    , message = "생성 성공"
            )
    })
    @ResponseBody
    @GetMapping("/{id}")
    public CommonResult detail(@PathVariable("id") Long id) {

        BundleResponseDto result = bundleService.findById(id);

        return responseService.getResult(result);
    }

    @ApiOperation(value = "카드집 생성", notes = "카드집을 생성합니다.")
    @ResponseBody
    @PostMapping("/")
    public CommonResult create(@ApiParam @Validated @RequestBody BundleSaveDto form) {

        Long id = bundleService.save(form);
        return responseService.getResult(id);
    }

    @ApiOperation(value = "카드집에 카드를 추가합니다.", notes = "카드집에 카드를 추가합니다.")
    @ResponseBody
    @PostMapping("/add-card")
    public CommonResult addCard(@ApiParam @Validated @RequestBody BundleAddCardDto form) {

        bundleService.addPost(form);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "카드 삭제", notes = "카드집에서 카드를 삭제합니다.")
    @ResponseBody
    @PostMapping("/delete-card")
    public CommonResult deteteCard(@ApiParam @Validated @RequestBody BundleDeleteCardDto form) {
        bundleService.deletePost(form);
        return responseService.getSuccessResult();
    }

    /**
     * Swagger Response API docs 용 클래스
     */

    private class BundleDetailClass extends CommonResult<BundleResponseDto>{ }
}
