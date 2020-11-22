package com.wangzaiplus.test.service;

import com.wangzaiplus.test.dto.FundDto;
import com.wangzaiplus.test.pojo.Fund;

import java.util.List;
import java.util.Set;

public interface FundService {

    Set<FundDto> combine(int[] earnings, Integer year);

    void update(List<FundDto> list);

    List<Fund> selectByType(FundDto fundDto);

}
