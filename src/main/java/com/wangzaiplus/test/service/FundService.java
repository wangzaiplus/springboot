package com.wangzaiplus.test.service;

import com.wangzaiplus.test.dto.FundDto;

import java.util.List;

public interface FundService {

    List<FundDto> search(FundDto fundDto);

    List<FundDto> combine(FundDto fundDto);

    void update(List<FundDto> list);

}
