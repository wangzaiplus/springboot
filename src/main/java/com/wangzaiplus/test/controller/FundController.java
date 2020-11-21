package com.wangzaiplus.test.controller;

import com.google.common.collect.Lists;
import com.wangzaiplus.test.common.Constant;
import com.wangzaiplus.test.common.ServerResponse;
import com.wangzaiplus.test.dto.FundDto;
import com.wangzaiplus.test.util.BeanUtils;
import com.wangzaiplus.test.util.FundUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fund")
@Slf4j
public class FundController {

    @PostMapping("fund")
    public ServerResponse fund() {
        List<FundDto> resultList = Lists.newArrayList();

        List<List<String>> lists = FundUtils.getFundData(Constant.FundType.QDII.getCode());
        for (List<String> list : lists) {
            FundDto dto = FundDto.builder().build();
            try {
                BeanUtils.convert(list, dto);
                resultList.add(dto);
            } catch (Exception e) {
                log.error("convert error");
            }
        }

        return ServerResponse.success(resultList);
    }

}
