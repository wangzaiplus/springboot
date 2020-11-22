package com.wangzaiplus.test.service.impl;

import com.wangzaiplus.test.dto.FundDto;
import com.wangzaiplus.test.mapper.FundMapper;
import com.wangzaiplus.test.pojo.Fund;
import com.wangzaiplus.test.service.FundService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class FundServiceImpl implements FundService {

    @Autowired
    private FundMapper fundMapper;

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

    @Override
    public void update(List<FundDto> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        for (FundDto fundDto : list) {
            Fund fund = toFund(fundDto);
            // TODO lock
            Fund oldFund = fundMapper.selectByCodeAndType(fund);
            if (null == oldFund) {
                fundMapper.insert(fund);
            } else {
                fund.setId(oldFund.getId());
                fundMapper.update(fund);
            }
        }
    }

    private Fund toFund(FundDto fundDto) {
        Fund fund = Fund.builder().build();
        BeanUtils.copyProperties(fundDto, fund);
        return fund;
    }

    @Override
    public List<Fund> selectByType(FundDto fundDto) {
        return fundMapper.selectByType(toFund(fundDto));
    }

}
