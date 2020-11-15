package com.wangzaiplus.test.controller;

import com.google.common.collect.Lists;
import com.wangzaiplus.test.common.ServerResponse;
import com.wangzaiplus.test.dto.FundDto;
import com.wangzaiplus.test.util.TableParseUtils;
import com.wangzaiplus.test.util.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fund")
@Slf4j
public class FundController {

    @Autowired
    private OkHttpUtils okHttpUtils;

    @PostMapping("fund")
    public ServerResponse fund() {
        List<FundDto> resultList = Lists.newArrayList();
        String get = okHttpUtils.doGet("http://fund.eastmoney.com/api/Dtshph.ashx?t=1&c=yndt&s=desc&issale=1&page=1&psize=10&callback=jQuery18307941559304212775_1605318491214&_=1605318491251");
        List<List<String>> lists = TableParseUtils.parse(get);
        for (List<String> list : lists) {
            resultList.add(FundDto.builder()
                    .code(list.get(0))
                    .name(list.get(1))
                    .earningOf1(list.get(2))
                    .earningOf2(list.get(3))
                    .earningOf3(list.get(4))
                    .earningOf5(list.get(5))
                    .build());
        }

        return ServerResponse.success(resultList);
    }

}
