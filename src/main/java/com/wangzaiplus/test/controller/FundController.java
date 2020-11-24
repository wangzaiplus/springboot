package com.wangzaiplus.test.controller;

import com.wangzaiplus.test.common.ServerResponse;
import com.wangzaiplus.test.dto.FundDto;
import com.wangzaiplus.test.service.FundService;
import com.wangzaiplus.test.util.FundUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fund")
@Slf4j
public class FundController {

    @Autowired
    private FundService fundService;

    @PostMapping("getFundDtoList")
    public ServerResponse getFundDtoList(@RequestBody FundDto fundDto) {
        List<FundDto> fundDtoList = FundUtils.getFundDtoList(fundDto.getType());
        fundService.update(fundDtoList);
        return ServerResponse.success();
    }

    @PostMapping("search")
    public ServerResponse search(@RequestBody FundDto fundDto) {
        List<FundDto> list = fundService.search(fundDto);
        return ServerResponse.success(list);
    }

    @PostMapping("combine")
    public ServerResponse combine(@RequestBody FundDto fundDto) {
        return ServerResponse.success(fundService.combine(fundDto));
    }

}
