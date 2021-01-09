package com.wangzaiplus.test.service;

import com.wangzaiplus.test.common.ServerResponse;
import com.wangzaiplus.test.dto.FundDto;
import com.wangzaiplus.test.dto.SearchFormDto;

import java.util.List;

public interface FundService {

    ServerResponse rank(FundDto fundDto);

    ServerResponse search(SearchFormDto searchFormDto);

    void update(List<FundDto> list);

    ServerResponse getSearchFormDto();


}
