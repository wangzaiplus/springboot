package com.wangzaiplus.test.service.impl;

import com.google.common.collect.Lists;
import com.wangzaiplus.test.common.Constant;
import com.wangzaiplus.test.dto.FundDto;
import com.wangzaiplus.test.mapper.FundMapper;
import com.wangzaiplus.test.pojo.Fund;
import com.wangzaiplus.test.service.FundService;
import com.wangzaiplus.test.util.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FundServiceImpl implements FundService {

    @Autowired
    private FundMapper fundMapper;

    @Override
    public List<FundDto> combine(List<String> list, FundDto fundDto) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        List<List<FundDto>> lists = Lists.newArrayList();
        for (String orderByType : list) {
            FundDto dto = FundDto.builder()
                    .type(fundDto.getType())
                    .orderBy(orderByType)
                    .sort(Constant.FundSortType.DESC.getType())
                    .limit(fundDto.getLimit())
                    .build();
            lists.add(fundMapper.selectByType(dto));
        }

        ListUtils listUtils = new ListUtils<FundDto>();
        return listUtils.intersection(lists, true);
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

    @Override
    public List<FundDto> search(FundDto fundDto) {
        return fundMapper.selectByType(fundDto);
    }

    private Fund toFund(FundDto fundDto) {
        Fund fund = Fund.builder().build();
        BeanUtils.copyProperties(fundDto, fund);
        return fund;
    }

}
