package com.wangzaiplus.test.service.impl;

import com.wangzaiplus.test.dto.FundDto;
import com.wangzaiplus.test.service.FundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class FundServiceImpl implements FundService {

    @Override
    public Set<FundDto> combine(int[] earnings, Integer year) {
        log.info("combine");
        return null;
    }

    private Set<FundDto> getCombinedFund() {
        return null;
    }

    private static Set<FundDto> intersection(Set<FundDto> set1, Set<FundDto> set2) {
        set1.retainAll(set2);
        return set1;
    }

}
