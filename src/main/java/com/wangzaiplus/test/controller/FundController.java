package com.wangzaiplus.test.controller;

import com.wangzaiplus.test.common.ServerResponse;
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
        String get = okHttpUtils.doGet("http://fund.eastmoney.com/api/Dtshph.ashx?t=1&c=yndt&s=desc&issale=1&page=1&psize=30&callback=jQuery18307941559304212775_1605318491214&_=1605318491251");
        List<List<String>> lists = TableParseUtils.parse(get);
        for (List<String> list : lists) {
            System.out.println(list);
        }

        return ServerResponse.success();
    }

}
