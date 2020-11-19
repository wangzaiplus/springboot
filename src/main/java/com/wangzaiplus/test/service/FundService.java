package com.wangzaiplus.test.service;

import com.wangzaiplus.test.dto.FundDto;

import java.util.Set;

public interface FundService {

    Set<FundDto> combine(int[] earnings, Integer year);

}
